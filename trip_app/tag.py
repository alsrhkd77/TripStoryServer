# 여행 태그 관리 모음
from trip_app import db


# 새로운 여행 태그 추가 성공시 True 실패시 False 반환
def add_tag(user_id, tag_name):
    conn = db.get_connection()
    cursor = conn.cursor()
    rs = None
    try:
        cursor.execute(
            f'''SELECT tag_name FROM tag WHERE tag_name =\'{tag_name}\''''
        )
        rs = cursor.fetchall()
        if not rs:
            query = f'''INSERT INTO tag VALUES(\'{user_id}\', \'{tag_name}\')'''
            cursor.execute(query)
            conn.commit()
        rs = True
    except Exception as ex:
        rs = False
    finally:
        cursor.close()
        conn.close()
        return rs


# 현재 사용자의 여행 태그를 모두 읽어옴 값이 성공하면 결과를 실패하면 False 반환
def get_tag(user_id):
    conn = db.get_connection()
    cursor = conn.cursor()
    results = {}
    try:
        cursor.execute(
            f'''SELECT * FROM tag WHERE user_id = \'{user_id}\''''
        )
        rs = cursor.fetchall()
        i = 1
        for row in rs:
            results[i] = row[1]
            i += 1
        rs = results
    except Exception as ex:
        rs = False
    finally:
        cursor.close()
        conn.close()
    return rs
