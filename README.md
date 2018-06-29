#  :school: ezWord - Easy Word 

Ứng dụng học tiếng Anh tích hợp từ điển tra cứu :notebook_with_decorative_cover: 
với nhiều chế độ ôn tập khác nhau, giúp trình độ tiếng Anh tăng nhanh như bay :airplane:

![alt text][logo]

[logo]: /img/icon.png "App Icon"

Tình trạng tích hợp:

- Từ điển Anh - Anh
- *(Updating)*

## Application Flow 

- Tra từ bằng app: **Main Activty**(*Home Fragment*) :left_right_arrow: **Single Word Activity** 
- Tra từ bằng menu: *Text Display Application* :left_right_arrow: **Request Search Activity**
- Ôn tập: **Main Activty**(*Home Fragment*) :left_right_arrow: **Main Activty**(*Review Fragment*)
- Xem lịch sử: **Main Activty** :left_right_arrow: **History Activity**
- Xem từ đã đánh dấu: **Main Activty** :left_right_arrow: **Bookmark Activity**
- Đồng bộ hóa dữ liệu: **Main Activty** :left_right_arrow: **Sync Activity**
- Thay đổi cài đặt: **Main Activty** :left_right_arrow: **Settings Activity**
- Xem trợ giúp: **Main Activty** :left_right_arrow: **Help Activity**
- Xem thông tin nhóm: **Main Activty** :left_right_arrow: **AboutUs Activity**

## Class Structure

Bao gồm Background class xử lý logic nền ứng dụng và IU class xử lý giao diện ứng dụng

### Background

- *Database*  - Các class xử lý cơ sở dữ liệu
    + **DictionaryContract**  
    
    Lưu thông tin thiết lập ban đầu của bảng **Dictionary**
    + **DictionaryDBHelper**
    
    Xử lý trực tiếp trên **SQLiteHelper** với bảng **Dictionary**, cung cấp các hàm:
        
        - open: mở Database để truy xuất
        - close: đóng Database sau khi truy xuất
        
    + **DictionaryProvider**
    
    Cho phép truy xuất dữ liệu từ bên ngoài, cung cấp các Uri:
    
        - content://com.ezword.ezword/Word
        Lấy danh sách tất cả từ vựng
        - content://com.ezword.ezword/Word/[id]
        Lấy từ vựng có id = [id]
        - content://com.ezword.ezword/Word/eng?=[eng]
        Lấy từ vựng có eng = [eng]
        - content://com.ezword.ezword/Word/suggestion?=[suggestion]
        Lấy những từ vựng đề xuất cho từ tìm kiếm suggestion = [suggestion]
        - content://com.ezword.ezword/Word/rand
        Lấy từ vựng ngẫu nhiên
    + **FlashCardContract**
    
    Lưu thông tin thiết lập ban đầu bảng **FlashCard**
    + **LocalData**
    
    Cổng giao tiếp **TinyDB** với ứng dụng, cung cấp các hàm:
    
        - add/remove/getHistory(String/Word)
        Thêm/Xóa/Lấy các từ trong danh sách lịch sử
        - add/remove/getBookmark(String/Word)
        Thêm/Xóa/Lấy các từ trong danh sách đánh dấu
        - store/getToken(String)
        Lưu trữ/Lấy Token để đăng nhập
        - checkSynced(String)
        Kiểm tra tài khoản đã đồng bộ hay chưa
    + **TinyDB**

    Thư viện cho phép sử dụng nhanh **SharedPreference**
- **Dictionary** - Các class cung cấp đối tượng cơ bản để thao tác
    + **Dictionary**
    
    Cổng giao tiếp giao diện và **Database** qua **ContentResolver**. Cung cấp các hàm:
    
        - search(String)
        Tìm kiếm từ vựng bằng từ khóa
        - getWordById(int)
        Tìm kiếm từ vựng bằng id
        - getAllWords()
        Lấy danh sách tất cả từ vựng
        - getRecommendations(String, int)
        Lấy danh sách từ đề xuất cho từ khóa với giới hạn limit
        - getRandomWord()
        Lấy từ vựng ngẫu nhiên
        - checkFlashCardExist(int)
        Kiểm tra tồn tại của FlashCard
        - addFlashCardToDatabase(int)
        Thêm FlashCard vào Database với từ vựng có id = id
    + **FlashCard**
    
    Class đối tượng **Flashcard** để thể hiện **FlashCard**
    + **Word**
    
    Class đối tượng **Word** để thể hiện từ vựng trong **Dictionary**
- **Quiz** - Các class cung cấp đối tượng để xử lý câu hỏi ôn tập/ ghi nhận tiến độ
    + **MultipleChoiceQuiz**
    
    Kế thừa Quiz, cài đặt phương thức xử lý cho các câu hỏi MultipleChoice
    + **TextInputQuiz**
    
    Kế thừa Quiz, cài đặt phương thức xử lý cho các câu hỏi TextInput
    + **Quiz**
    
    Class đối tượng tạo ra để ôn tập, cung cấp hàm:
    
        - createView(View)
        Đưa thông tin cần thiết vào View và trả về để hiển thị
        - getResult(String/int)
        Nhận đáp án, trả về kết quả
    + **QuizGenerator**
    
    Sinh ra các Quiz, cổng giao tiếp giao diện và database, cung cấp hàm:
    
        - getQuiz()
        Trả về đối tượng Quiz để hiển thị và kiểm tra
- **Services** - Dịch vụ chạy ngầm
    + **AlarmReceiver**
    
    Nhận thông báo Alarm của hệ thống và xử lý, gọi Notification để xử lý
    + **NotificationService**
    
    Dịch vụ chạy ngầm, được Alarm gọi để hiển thị thông tin
- **User**
    + **User**
    
    Xử lý giao tiếp đăng nhập/đăng kí/đồng bộ thông tin dữ liệu người dùng với máy chủ, cung cấp các hàm:
    
        - isLoggedIn()
        Kiểm tra tình trạng đăng nhập
        - logIn(Callback)
        Xử lý đăng nhập, sau đó gọi lại Callback
        - signUp(Callback)
        Xử lý đăng kí, sau đó gọi lại Callback
        - getBasicAuthenticationEncoding(username, password)
        Chuyển username/password thành Header BasicAuth
        - syncData(Callback)
        Xử lý đồng bộ dữ liệu, sau đó gọi lại Callback
        - downloadData(Callback)
        Xử lý tải xuống dữ liệu, sau đó gọi lại Callback
        - uploadData(Callback)
        Xử lý tải lên dữ liệu, sau đó gọi lại Callback
        - setToken(String)
        Lưu trữ lại Token đăng nhập
        - setLastSignInUserName(String)
        Ghi nhận lại lịch sử đăng nhập User
    

### UI

- **Activities** - Các class xử lý giao diện chỉ hiển thị, ít hàm xử lý
    + **AboutUsActivity**
    
    Xử lý giao diện giới thiệu nhóm
    + **BookmarkActivity**
    
    Xử lý giao diện từ đánh dấu
    + **HelpActivity**
    
    Xử lý giao diện trợ giúp
    + **HistoryActivity**
    
    Xử lý giao diện lịch sử tra cứu
    + **SettingsActivity**
    
    Xử lý giao diện cài đặt
- **Adapters** - Adapter ứng với các View dạng danh sách của Android
    + **MainViewPagerAdapter**
    
    Xử lý ViewPager của MainActivity
    + **WordListAdapter**
    
    Xử lý RecyclerList hiển thị danh sách từ vựng
    + **WordSearchViewAdapter**
    
    Xử lý RecyclerList cho SearchView hiển thị danh sách từ được đề xuất
- **Fragments** - Các Fragment xử lý hiển thị giao diện ứng dụng
    + **HomeFragment**
    
    Xử lý Fragment giao diện chính
    + **QuizQuestionFragment**
    
    Xử lý Fragment giao diện câu hỏi ôn tập
    + **ReviewFragment**

    Xử lý giao diện ôn tập
    + **SettingsFragment**
    
    Xử lý giao diện cài đặt
    + **WordMatchingFragment**
    
    Xử lý giao diện câu hỏi ôn tập dạng WordMatching
- **Main Activities** - Các class giao diện chính/ xử lý nhiều
    + **MainActivity**
    
    Xử lý giao diện chính
    + **QuizActivity**
    
    Xử lý giao diện ôn tập câu hỏi
    + **RequestSearchActivity**
    
    Xử lý giao diện từ vựng, nhận request của menu của chương trình hiển thị Text khác
    + **ReviseActivity**
    
    Xử lý giao diện ôn tập
    + **SearchableActivity**
    + **SingleWordActivity**
    
    Xử lý giao hiện hiển thị thông tin từ vựng
    + **SyncActivity**
    
    Xử lý giao diện đồng bộ dữ liệu người dùng

## Database Structure

Bao gồm các bảng sau:
- **Word** (Lưu trữ dữ liệu từ điển)
    + **WordID**
    + WordEnglish
    + Type
    + Definition
    + PhoneticSpelling
- **FlashCard** (Lưu trữ dữ liệu Flashcard do người dùng tạo)
    + **CardID**
    + WordID
    + Note
    + EF
    + NextLearningPoint
    + ConsecutiveCorrect
- ParametersTable (Lưu trữ các tham số mặc định của chương trình) - Sử dụng SharedPreference thay thế

## Data Server

Viết bằng **Python** với framework **Flask**

Khởi động ở port 5555, nhận các request có header 'Content-Type': 'application/json'

Cung cấp các API:

1. /api/users [Giao thức 'POST']

Nhận request có body gồm username/password

*Thất bại*: Trả response 400

**Thành công**: Trả response 201, bao gồm thông tin username/user_id

2. /api/check_token

Nhận request Basic Auth token/"random text"

*Thất bại*: Trả 'token-check': 'fail'

**Thành công**: Trả 'token-check': 'true'

3. /api/token

Nhận request Basic Auth user/password

*Thất bại*: Trả response 400

**Thành công**: Trả 'token-check': 'true'

4. /api/resource

Nhận request Basic Auth token/"random text" hoặc user/password

*Thất bại*: Trả response 400

**Thành công**: Trả thông tin 'bookmark', 'history'

5. /api/update/history

Nhận request Basic Auth token/"random text" hoặc user/password, body 'history'

*Thất bại*: Trả response 400

**Thành công**: Trả thông tin 'history'

6. /api/update/bookmark

Nhận request Basic Auth token/"random text" hoặc user/password, body 'bookmark'

*Thất bại*: Trả response 400

**Thành công**: Trả thông tin 'bookmark'

## External Library

Thư viện bên ngoài:

- MPAndroidChart
- TinyDB
- okhttp3