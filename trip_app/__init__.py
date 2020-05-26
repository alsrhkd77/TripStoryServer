from flask import Flask, render_template, request, redirect, session
from trip_app import sign_up, sign_in

application = Flask(__name__)
application.config['SEND_FILE_MAX_AGE_DEFAULT'] = 0
application.config['SECRET_KEY'] = 'asldjfhsadkjfbasdf'


@application.route('/')
def index():
    if 'user_id' in session:
        return redirect('/main')
    return render_template("index.html")


@application.route('/login', methods=['POST'])
def login():
    user_id = request.form.get('id')
    password = request.form.get('password')
    # 로그인한 적 없는 경우
    if 'user_id' not in session:
        # 로그인 성공
        if sign_in.do_login(user_id, password):
            session['user_id'] = user_id
            return redirect('/main')
        # 로그인 실패
        else:
            return redirect('/')
    # 이미 로그인된 경우
    else:
        return redirect('/main')


@application.route('/id-doubled', methods=['post'])
def id_check():
    id = request.data.decode('utf-8')
    return str(sign_up.is_id_doubled(id))


@application.route('/sign-up', methods=['post'])
def register_user():
    return str(request.form)


@application.route('/main')
def main():
    cur_user = session['user_id']
    return render_template("main.html", id=cur_user)
