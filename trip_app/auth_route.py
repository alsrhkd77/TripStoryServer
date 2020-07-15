from flask import request
from flask import session
from flask import redirect
from flask import render_template
from trip_app import application
from trip_app import sign_in
from trip_app import sign_up
import json


# Trip Story 회원 로그인
@application.route('/login', methods=['POST'])
def login():
    user_id = request.form.get('id')
    password = request.form.get('password')
    # 로그인한 적 없는 경우
    if 'user_id' not in session:
        # 로그인 성공
        if sign_in.do_login(user_id, password):
            name = sign_in.get_ts_member_name(user_id)
            session['user_id'] = user_id
            session['user_name'] = name
            return redirect('/main')
        # 로그인 실패
        else:
            return redirect('/')
    # 이미 로그인된 경우
    else:
        return redirect('/main')


# 구글, 페이스북 로그인
@application.route('/login-social', methods=['POST'])
def login_social():
    # id, name 값을 가져옴
    data = request.data.decode('utf8')
    j_data = json.loads(data)
    id = j_data['id']
    name = j_data['name']
    application.logger.debug(j_data)
    if sign_in.do_login_social(id, name):
        session['user_id'] = id
        session['user_name'] = name
        application.logger.debug(session['user_id'])
        return 'login-success'
    else:
        return 'login-failed'


@application.route('/logout')
def logout():
    if 'user_id' in session:
        session.pop('user_id', None)
        return redirect('/')
    return redirect('/')


@application.route('/id-doubled', methods=['post'])
def id_check():
    id = request.data.decode('utf-8')
    return str(sign_up.is_id_doubled(id))


@application.route('/sign-up', methods=['post'])
def register_user():
    id = request.form.get('id')
    name = request.form.get('name')
    password = request.form.get('password')
    # 회원가입 성공 시
    if sign_up.add_user(id, name, password):
        return render_template('/signup-success.html')
    else:
        return render_template('/signup-failed.html')