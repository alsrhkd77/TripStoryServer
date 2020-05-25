from flask import Flask, render_template, request
import json
from trip_app import sign_up

application = Flask(__name__)
application.config['SEND_FILE_MAX_AGE_DEFAULT'] = 0
application.config['SECRET_KEY'] = 'asldjfhsadkjfbasdf'


@application.route('/')
def index():
    return render_template("index.html")


@application.route('/login', methods=['POST'])
def do_login():
    id = request.form.get('id')
    password = request.form.get('password')
    user_info = f'''user id {id} <br> user password {password}'''
    return user_info


@application.route('/id-doubled', methods=['post'])
def check_id():
    id = request.data.decode('utf-8')
    return str(sign_up.is_id_doubled(id))


@application.route('/sign-up', methods=['post'])
def add_user():
    return json.loads(request.data)

