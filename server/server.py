from flask import Flask, request, jsonify, abort, url_for, g
from flask_sqlalchemy import SQLAlchemy
from flask_httpauth import HTTPBasicAuth
from passlib.apps import custom_app_context as pwd_context
from itsdangerous import (TimedJSONWebSignatureSerializer
                          as Serializer, BadSignature, SignatureExpired)
from database import *
import json

app = Flask(__name__)

app.config['SECRET_KEY'] = 'the quick brown fox jumps over the lazy dog'
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///users.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)
auth = HTTPBasicAuth()

class User(db.Model):
    __tablename__ = 'users'
    id = db.Column(db.Integer, primary_key = True)
    username = db.Column(db.String(32), index = True)
    password_hash = db.Column(db.String(128))
    history = db.Column(db.Text)
    bookmark = db.Column(db.Text)
    countDownTime = db.Column(db.Integer)
    numWordInSession = db.Column(db.Integer)
    def __repr__(self):
        return '<User %r>' % self.username
    
    def hash_password(self, password):
        self.password_hash = pwd_context.encrypt(password)

    def verify_password(self, password):
        return pwd_context.verify(password, self.password_hash)
    
    def generate_auth_token(self, expiration = 3600):
        s = Serializer(app.config['SECRET_KEY'], expires_in = expiration)
        return s.dumps({ 'id': self.id })

    def set_history(self, history):
        self.history = history

    def set_bookmark(self, bookmark):
        self.bookmark = bookmark
    
    def set_count_down_time(self, countDownTime):
        self.countDownTime = countDownTime

    def set_num_word_in_session(self, numWordInSession):
        self.numWordInSession = numWordInSession    
    
    @staticmethod
    def verify_auth_token(token):
        s = Serializer(app.config['SECRET_KEY'])
        try:
            data = s.loads(token)
        except SignatureExpired:
            return None # valid token, but expired
        except BadSignature:
            return None # invalid token
        user = User.query.get(data['id'])
        return user

@app.route('/api/users', methods = ['POST'])
def new_user():
    username = request.json.get('username')
    password = request.json.get('password')
    print (username, password)
    if username is None or password is None:
        abort(400) # missing arguments
    if User.query.filter_by(username = username).first() is not None:
        abort(400) # existing user
    user = User(username = username)
    user.hash_password(password)
    db.session.add(user)
    db.session.commit()
    return jsonify({ 'username': user.username }), 201,{'Location': url_for('get_user', id = user.id, _external = True)}

@app.route('/api/users/<int:id>')
def get_user(id):
    user = User.query.get(id)
    if not user:
        abort(400)
    return jsonify({'username': user.username})

@app.route("/")
def hello():
    return "Hello World!"

@auth.verify_password
def verify_password(username_or_token, password):
    # first try to authenticate by token
    user = User.verify_auth_token(username_or_token)
    if not user:
        # try to authenticate with username/password
        user = User.query.filter_by(username = username_or_token).first()
        if not user or not user.verify_password(password):
            return False
    g.user = user
    return True

@app.route('/api/check_token')
def check_token():
    token = request.authorization.username
    print (token)
    user = User.verify_auth_token(token)
    if not user:
        return jsonify({'token-check':'fail'})
    return jsonify({'token-check':'success'})

@app.route('/api/token')
@auth.login_required
def get_auth_token():
    token = g.user.generate_auth_token()
    return jsonify({ 'token': token.decode('ascii'),'username': g.user.username })

@app.route('/api/resource')
@auth.login_required
def get_resource():
    return jsonify({ 'history': '%s' % g.user.history, 'bookmark': '%s' % g.user.bookmark, 'count_down_time': '%s' % g.user.countDownTime,
    'num_of_word_in_session': '%s' % g.user.numWordInSession })

@app.route('/api/update_all', methods = ['POST'])
@auth.login_required
def update_all():
    history = request.json.get('history')
    bookmark = request.json.get('bookmark')
    countDownTime = request.json.get('count_down_time')
    numWordInSession = request.json.get('num_of_word_in_session')
    g.user.set_history(history)
    g.user.set_bookmark(bookmark)
    g.user.set_count_down_time(countDownTime)
    g.user.set_num_word_in_session(numWordInSession)
    print("hello")
    db.session.commit()
    return True


@app.route('/api/update/history', methods = ['POST'])
@auth.login_required
def update_history():
    history = request.json.get('history')
    g.user.set_history(history)
    return jsonify({'history': '%s' % g.user.history})
    
@app.route('/api/update/bookmark', methods = ['POST'])
@auth.login_required
def update_bookmark():
    bookmark = request.json.get('bookmark')
    g.user.set_bookmark(bookmark)
    return jsonify({'bookmark': '%s' % g.user.bookmark})
    
if __name__ == '__main__':
    app.debug = True
    app.run(host = '0.0.0.0',port = 5555)
