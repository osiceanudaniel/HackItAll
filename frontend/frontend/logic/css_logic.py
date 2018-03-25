from flask import session


def set_active_link(page):
    if page == 'home':
        session['home'] = 'active'
        session['my_profile'] = ''
        session['external_products'] = ''

    elif page == 'my_profile':
        session['home'] = ''
        session['my_profile'] = 'active'
        session['external_products'] = ''
    elif page == 'external_products':
        session['home'] = ''
        session['my_profile'] = ''
        session['external_products'] = 'active'
    else:
        session['home'] = ''
        session['my_profile'] = ''
        session['external_products'] = ''
