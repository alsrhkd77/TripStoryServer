from flask import session
from flask import jsonify
from flask import request
import json
from trip_app import application
from trip_app import tag


@application.route('/tags', methods=['POST'])
def get_tag():
    if 'user_id' in session:
        user_id = session['user_id']
    else:
        data = request.data.decode('utf-8')
        j_data = json.loads(data)
        user_id = j_data['id']
    application.logger.debug(user_id)
    result = tag.get_tag(user_id)
    application.logger.debug(result)
    if result:
        return jsonify(result)
    else:
        return "no-data"
