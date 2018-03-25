from frontend import app
from flask import render_template, session, request, redirect, url_for
from frontend.logic.css_logic import set_active_link
import requests


@app.route('/my_profile')
def my_profile():
    set_active_link('my_profile')

    # get category

    r = requests.get('http://192.168.0.103:8090/api/categories')

    print r

    category = r.json()

    # category = [
    # {
    #     'idCategory': 0,
    #     'dayExpiration': 10,
    #     'name': 'Lactate'
    # },
    # {
    #     'idCategory': 1,
    #     'dayExpiration': 5,
    #     'name': 'Carnuri'
    # },
    # {
    #     'idCategory': 2,
    #     'dayExpiration': 2,
    #     'name': 'Legume'
    # },
    # {
    #     'idCategory': 3,
    #     'dayExpiration': 3,
    #     'name': 'Fructe'
    # }]

    return render_template('/views/my_profile.html', session=session, category=category)


@app.route('/create_product', methods=['POST'])
def create_product():
    data = request.form.to_dict()

    requests.post('http://192.168.0.103:8090/api/addproduct?name={}&quantity={}&id_category={}'.format(
            data['product_name'],
            data['quantity'],
            data['category']))

    # r = requests.get('http://192.168.0.103:8090/api/myproducts')
    #
    # products = r.json()

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
    #
    # return render_template('/views/home.html', data=products, session=session)

    return redirect(url_for('home'))


