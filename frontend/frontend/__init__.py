from flask import Flask
app = Flask(__name__)

import frontend.views
import frontend.test.test
