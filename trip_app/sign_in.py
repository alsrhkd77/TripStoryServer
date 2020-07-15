from trip_app import db


# Trip Story 회원 로그인
def do_login(id, password):
    conn = db.get_connection()
    cursor = conn.cursor()
    rs = None
    try:
        cursor.execute(
            f'''SELECT * FROM auth WHERE user_id = \'{id}\' and user_password = \'{password}\''''
        )
        rs = cursor.fetchall()
        if rs:
            rs = True
        else:
            rs = False
    finally:
        cursor.close()
        conn.close()
        return rs


# Trip Story 회원 로그인의 경우 이름을 가져오기 위해 사용
def get_ts_member_name(id):
    conn = db.get_connection()
    cursor = conn.cursor()
    rs = None
    try:
        cursor.execute(
            f'''SELECT user_name FROM auth WHERE user_id = \'{id}\''''
        )
        rs = cursor.fetchall()
    finally:
        cursor.close()
        conn.close()
        return rs


# 현재 소셜 로그인한 계정이 DB에 없는 경우 추가함
def do_login_social(id, name):
    conn = db.get_connection()
    cursor = conn.cursor()
    rs = None
    try:
        cursor.execute(
            f'''SELECT * FROM users WHERE user_id = \'{id}\' and user_name = \'{name}\''''
        )
        rs = cursor.fetchall()
        if not rs:
            cursor.execute(
                f'''INSERT INTO users VALUES(\'{id}\', \'{name}\' , null)'''
            )
            conn.commit()
        rs = True
    except Exception as ex:
        rs = False
    finally:
        cursor.close()
        conn.close()
        return rs
