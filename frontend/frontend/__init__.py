from flask import Flask
import atexit
import requests

from apscheduler.schedulers.background import BackgroundScheduler
from apscheduler.triggers.interval import IntervalTrigger


app = Flask(__name__)


def print_date_time():
    r = requests.get('http://192.168.0.103:8090/api/notificationday')
    if r.json() == 1:
        requests.get('http://192.168.0.103:8090/send')


scheduler = BackgroundScheduler()
scheduler.start()
scheduler.add_job(
    func=print_date_time,
    trigger=IntervalTrigger(seconds=5000),
    id='printing_job',
    name='Print date and time every five seconds',
    replace_existing=True)
# Shut down the scheduler when exiting the app
atexit.register(lambda: scheduler.shutdown())

app.secret_key = 'super secret key'
app.config['SESSION_TYPE'] = 'filesystem'

import frontend.views.my_profile
import frontend.views.home
import frontend.views.external_products
