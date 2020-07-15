from flask import Flask
from flask import render_template
from flask import redirect
from flask import session
from flask import jsonify
import logging
from trip_app import sign_up
from trip_app import sign_in
from trip_app import tag


application = Flask(__name__)
application.config['SEND_FILE_MAX_AGE_DEFAULT'] = 0
application.config['SECRET_KEY'] = 'asldjfhsadkjfbasdf'
application.config['UPLOAD_FOLDER'] = '/trip_app/static/upload-images'
# application.config['JSON_AS_ASCII'] = False
application.logger.setLevel(logging.DEBUG)


# 웹 사이트 초기화면
@application.route('/')
def index():
    application.logger.debug("")
    if 'user_id' in session:
        return redirect('/main')
    return render_template("index.html")


# Trip Story 메인 화면
@application.route('/main')
def main():
    user_name = session['user_name']
    return render_template("main.html", name=user_name)


# 프로필 정보 화면
@application.route('/profile')
def profile():
    user_name = session['user_name']
    return render_template("profile.html", name=user_name)


# 좋아요 누른 피드 모음 화면
@application.route('/likes')
def like():
    user_name = session['user_name']
    return render_template('/likes.html', name=user_name)


# 피드(게시물) 추가 페이지
@application.route('/add-feed')
def add_feed():
    user_name = session['user_name']
    return render_template('/add-feed.html', name=user_name)


# 사용자 회원가입, Trip Story 회원 로그인, 구글 로그인, 페이스북 로그인 라우트
from trip_app import auth_route


# 여행 태그 관련 라우트
from trip_app import tag_route

# 피드(게시물) 관련 라우트
from trip_app import feed_route


@application.route('/dummy-data', methods=['POST'])
def testww():
    data = {1: '이종민', 2: '장희숙', 3: '임영호', 4: '권순각', 5: '권오준', 6: '권오준',7: '권오준', 8: '권오준', 9: '권오준', 10: '권오준', 11: '권오준', 12: '권오준', 13: '권오준' ,15: '권오준'}
    return jsonify(data)


