#  ezWord

:notebook_with_decorative_cover: Ứng dụng học tiếng Anh tích hợp từ điển tra cứu

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

- [Database](####database)
    + DictionaryContract
    + DictionaryDBHelper
    + DictionaryProvider
    + FlashCardContract
    + LocalData
    + TinyDB
- Dictionary
    + Dictionary
    + FlashCard
    + Word
- Quiz
    + MultipleChoiceQuiz
    + TextInputQuiz
    + Quiz
    + QuizGenerator
- Services
    + AlarmReceiver
    + NotificationService
- User
    + User

#### Database
### UI

- Activities
    + AboutUsActivity
    + BookmarkActivity
    + HelpActivity
    + HistoryActivity
    + SettingsActivity
- Adapters
    + MainViewPagerAdapter
    + WordListAdapter
    + WordSearchViewAdapter
- Fragments
    + HomeFragment
    + QuizQuestionFragment
    + ReviewFragment
    + SettingsFragment
    + WordMatchingFragment
- Main Activities
    + MainActivity
    + QuizAcitivity
    + RequestSearchActivity
    + ReviseActivity
    + SearchableActivity
    + SingleWordActivity
    + SyncActivity

## Database Structure

Bao gồm các bảng sau:
- Dictionary (Lưu trữ dữ liệu từ điển)
- FlashCard (Lưu trữ dữ liệu Flashcard do người dùng tạo)
- ParametersTable (Lưu trữ các tham số mặc định của chương trình)

## Data Server

Viết bằng **Python** với framework **Flask**

Khởi động ở port 5555

Cung cấp các API:

1. /api/users
2. /api/check_token
3. /api/token

### /api/users

### /api/check_token

### /api/token

### /api/resource

### /api/update/history

### /api/update/bookmark'

## External Library

