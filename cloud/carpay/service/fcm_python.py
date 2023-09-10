


def send_push_notification(server_token, device_token, title, body):
    """Send a push notification using FCM."""

    import requests
    import json

    headers = {
        'Content-Type': 'application/json',
        'Authorization': 'key=' + server_token,
    }

    body = {
        'notification': {
            'title': title,
            'body': body,
        },
        'to': device_token,
        'priority': 'high',
    }

    response = requests.post('https://fcm.googleapis.com/fcm/send', headers=headers, data=json.dumps(body))
    return response