from trip_app import db


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
