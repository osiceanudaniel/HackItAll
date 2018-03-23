from frontend import app


@app.route('/test')
def test():
    return 'Hello!'
