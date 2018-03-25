from frontend import app
from flask import render_template, session, request, redirect, url_for
from frontend.logic.css_logic import set_active_link
import requests


@app.route('/home')
def home():
    set_active_link('home')

    r = requests.get('http://192.168.0.103:8090/api/myproducts')

    print r.json()

    products = r.json()

    # products = [
    #     {
    #         'name': 'mar',
    #         'quantity': '8 bucati',
    #         'post_date': '12.02.2018',
    #         'expiration_date': '12.02.2023'
    #     },
    #     {
    #         'name': 'para',
    #         'quantity': '10 bucati',
    #         'post_date': '12.02.2018',
    #         'expiration_date': '12.02.2023'
    #     },
    #     {
    #         'name': 'lapte',
    #         'quantity': '2 litri',
    #         'post_date': '12.02.2018',
    #         'expiration_date': '12.02.2023'
    #     },
    #     {
    #         'name': 'carne',
    #         'quantity': '6 kg',
    #         'post_date': '12.02.2018',
    #         'expiration_date': '12.02.2023'
    #     }
    # ]

    return render_template('/views/home.html', data=products, session=session)


@app.route('/')
def index():
    set_active_link('home')

    products = [
        {
            'name': 'mar',
            'quantity': '8 bucati',
            'post_date': '12.02.2018',
            'expiration_date': '12.02.2023'
        },
        {
            'name': 'para',
            'quantity': '10 bucati',
            'post_date': '12.02.2018',
            'expiration_date': '12.02.2023'
        },
        {
            'name': 'lapte',
            'quantity': '2 litri',
            'post_date': '12.02.2018',
            'expiration_date': '12.02.2023'
        },
        {
            'name': 'carne',
            'quantity': '6 kg',
            'post_date': '12.02.2018',
            'expiration_date': '12.02.2023'
        }
    ]

    return render_template('/views/home.html', data=products, session=session)


@app.route('/share/<param>')
def share(param):

    # share request
    requests.post('http://192.168.0.103:8090/api/updatestatus?id_product={}&status=share'.format(param))

    return redirect(url_for('home'))


@app.route('/done/<param>')
def done(param):

    # done request
    requests.post('http://192.168.0.103:8090/api/updatestatus?id_product={}&status=done'.format(param))

    return redirect(url_for('home'))


@app.route('/getsession')
def get_session():
    r = requests.get('http://192.168.0.103:8090/api/notificationday')
    if r.json() == 1:
        print r.json()


    return 'Done'

