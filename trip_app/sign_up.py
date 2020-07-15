from trip_app import db


def is_id_doubled(user_id):
    conn = db.get_connection()
    cursor = conn.cursor()
    rs = None
    try:
        cursor.execute(f'''SELECT user_id FROM users WHERE user_id = {user_id};''')
        rs = cursor.fetchone()
        if not rs:
            rs = False
        else:
            rs = True
    finally:
        cursor.close()
        conn.close()
        return rs


def add_user(id, name, password):
    conn = db.get_connection()
    cursor = conn.cursor()
    try:
        cursor.execute(
            f'''INSERT INTO users VALUES(\'{id}\', \'{name}\', null);'''
        )
        cursor.execute(
            f'''INSERT INTO auth VALUES(\'{id}\', \'{password}\');'''
        )
        conn.commit()
    except Exception as e:
        return False
    finally:
        cursor.close()
        conn.close()
        return True
