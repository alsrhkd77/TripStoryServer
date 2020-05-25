from trip_app import db


def is_id_doubled(user_id):
    conn = db.get_connection()
    cursor = conn.cursor()
    cursor.execute(f'''SELECT user_id FROM user WHERE user_id = {user_id};''')
    if cursor.fetchone():
        db.close_connection(conn)
        return True
    else:
        db.close_connection(conn)
        return False
