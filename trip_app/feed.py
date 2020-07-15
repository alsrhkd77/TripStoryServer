from trip_app import db
import hashlib
from trip_app import application
from datetime import datetime


# 게시물 제목 값으로부터 해시 값 추출
# 해당 값은 피드(게시물)의 기본키로 사용
def get_hash_id(id, time):
    text = id.encode('utf-8') + time.encode('utf-8')
    enc = hashlib.md5()
    enc.update(text)
    hashed_id = enc.hexdigest()
    return hashed_id


# 피드(게시물)에 첨부된 이미지 경로를 DB에 저장
# get_hash_id를 통한 해당 피드의 기본키 해시값이 주어진 상태에서 호출되어야함!
def add_image(image_paths, feed_id):
    conn = db.get_connection()
    cursor = conn.cursor()
    try:
        # for image_path -> image
        for image in image_paths:
            cursor.execute(
                f'''INSERT INTO feed_images VALUES(\'{feed_id}\', \'{image}\')'''
            )
        conn.commit()
        status = True
    except Exception as ex:
        status = False
    finally:
        cursor.close()
        conn.close()
    return status


# 새로운 게시물 추가 성공시 게시물의 id를 반환 실패시 False 반환
def add_feed(hash_id, content, tag_name, user_id):
    conn = db.get_connection()
    cursor = conn.cursor()
    now = datetime.now()
    formatted_date = now.strftime('%Y-%m-%d %H:%M:%S')
    application.logger.debug("DB FEED : " + str(hash_id))
    application.logger.debug("DB FEED : " + str(content))
    application.logger.debug("DB FEED : " + str(tag_name))
    try:
        cursor.execute(
            f'''INSERT INTO feed 
                VALUES (\'{hash_id}\', \'{content}\', \'{tag_name}\', \'{formatted_date}\', \'{user_id}\', null)'''
        )
        conn.commit()
        status = True
    except Exception as ex:
        status = False
        return str(ex)
    finally:
        cursor.close()
        conn.close()
    return status


# 피드 이미지 리스트에서 이미지 각각 뽑기
def get_image_path(feed_id):
    conn = db.get_connection()
    cursor = conn.cursor()
    result = []
    sql = f"""SELECT * FROM feed_images WHERE feed_id = \'{feed_id}\'"""
    try:
        application.logger.debug(sql)
        cursor.execute(sql)
        rs = cursor.fetchall()
        for row in rs:
            application.logger.debug(row)
            result.append(row[1])
            application.logger.debug("FEED ID: " + row[0])
            application.logger.debug("IMG PATH: " + row[1])
    except Exception as ex:
        application.logger.debug(ex)
        result = False
    finally:
        cursor.close()
        conn.close()
    return result


# 사용자 아이디에서 이름을 가져와 반환
def get_name(user_id):
    conn = db.get_connection()
    cursor = conn.cursor()
    rs = None
    sql = f"""SELECT * FROM users WHERE user_id = \'{user_id}\'"""
    try:
        cursor.execute(sql)
        result = cursor.fetchall()
        for row in result:
            rs = row[1]
    except Exception as ex:
        rs = False
    finally:
        cursor.close()
        conn.close()
    return rs


# 전체 게시물에서 연관성을 지닌 피드를 가져와 타임라인 구성
def get_time_line():
    conn = db.get_connection()
    cursor = conn.cursor()
    sql = """SELECT * FROM feed ORDER BY upload_date, likes DESC"""
    results = []
    try:
        cursor.execute(sql)
        rs = cursor.fetchall()
        for row in rs:
            result = {'feed_id': row[0], 'content': row[1], 'tag_name': row[2],
                      'upload_date': row[3],'id': row[4],
                      'likes': row[5], 'name': get_name(row[4])}
            img_paths_temp = get_image_path(row[0])
            img_paths = []
            for i in img_paths_temp:
                img_path = "https://tripstory.ga/" + i
                img_paths.append(img_path)
            result['img_path'] = img_paths
            results.append(result)
    except Exception as ex:
        application.logger.debug(ex)
        results = False
    finally:
        cursor.close()
        conn.close()
    return results


# 자신이 올린 피드만 가져와서 개인 게시물 목록 구성
def get_my_feed(user_id):
    conn = db.get_connection()
    cursor = conn.cursor()
    sql = """SELECT * FROM feed WHERE user_id = %s"""
    results = []
    try:
        cursor.execute(sql % user_id)
        rs = cursor.fetchall()
        for row in rs:
            result = {'feed_id': row[0], 'content': row[1], 'tag_name': row[2], 'upload_date': row[3],
                      'id': row[4], 'likes': row[5], 'name': get_name(row[4])}
            img_paths_temp = get_image_path(row[0])
            img_paths = []
            for i in img_paths_temp:
                img_path = "https://tripstory.ga/" + i
                img_paths.append(img_path)
            result['img_path'] = img_paths
            results.append(result)
    except Exception as ex:
        application.logger.debug(ex)
        return False
    finally:
        cursor.close()
        conn.close()
    return results
