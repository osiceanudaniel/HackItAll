from frontend import app


@app.route('/')
def index():
    return 'Hello World!'
