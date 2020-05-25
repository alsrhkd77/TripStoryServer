import pymysql


def get_connection():
    conn = pymysql.connect(host='113.198.245.105', port=3306, user='seun',
                         passwd='1234', db='trip_story', charset='utf8')
    return conn


def close_connection(conn):
    try:
        conn.close()
    except Exception as e:
        print(e)
