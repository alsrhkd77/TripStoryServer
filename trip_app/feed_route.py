from flask import request
from flask import json
from flask import session
from flask import jsonify
import base64
from trip_app import application
from trip_app import feed
from trip_app import tag
from datetime import datetime


@application.route('/feed', methods=['POST'])
def new_feed():
    status = "add-failed"
    now = str(datetime.now())
    data = request.data.decode('utf-8')
    j_data = json.loads(data)

    if 'user_id' in session:
        user_id = session['user_id']
    else:
        user_id = j_data['id']

    content = j_data['content']
    tag_name = j_data['tag']

    # feed id 해쉬 함수를 통해 생성
    hash_id = feed.get_hash_id(user_id, now)
    application.logger.debug(hash_id)
    images = j_data['imgs']
    file_path = "static/upload-images/"
    image_paths = []
    index = 0
    for image in images:
        img_data = base64.b64decode(image)
        file_name = file_path + hash_id + str(index) + ".jpg"
        image_paths.append(file_name)
        with open(file_name, 'wb') as f:
            f.write(img_data)
        index += 1
    rs = tag.add_tag(user_id, tag_name)
    application.logger.debug(rs)
    if rs:
        application.logger.debug("tag add success")
        if feed.add_feed(hash_id, content, tag_name, user_id):
            application.logger.debug("feed add success")
            if feed.add_image(image_paths, hash_id):
                application.logger.debug("image path add success")
                status = "add-success"
    return status


@application.route('/feeds', methods=['post'])
def feeds():
    if 'user_id' in session:
        user_id = session['user_id']
    else:
        data = request.data.decode('utf-8')
        j_data = json.loads(data)
        user_id = j_data['id']
    result = feed.get_my_feed(user_id)
    application.logger.debug(result)
    if result:
        return jsonify(result)
    else:
        return "no-data"


@application.route('/time-line', methods=['post'])
def time_line():
    rs = feed.get_time_line()
    return jsonify(rs)
