from frontend import app
from flask import render_template, session
from frontend.logic.css_logic import set_active_link
import requests


@app.route('/external_products')
def external_products():
    set_active_link('external_products')

    r = requests.get('http://192.168.0.103:8090/api/otherproducts')

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

    return render_template('/views/external_products.html', data=products, session=session)
