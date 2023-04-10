#!/usr/bin/env python
from flask import Flask, render_template, Response
from flask import Blueprint,request,render_template,flash,redirect,url_for
from flask import current_app as current_app
from camera import Camera

aa= Flask(__name__)
@aa.route('/',methods=['GET'])
        

def index():
    test="hi"
    test2="hello"
    return render_template('index.html',title="temp",testDataHtml=test,testDataHtml2=test2)
 

def gen(camera):
   while True:
       frame = camera.get_frame()
       yield (b'--frame\r\n'
              b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')

@aa.route('/video_feed')
def video_feed():
    test3="CCTV"
    return Response(gen(Camera()),
                   mimetype='multipart/x-mixed-replace; boundary=frame')
 

if __name__ == '__main__':
   aa.run(host='0.0.0.0',debug=True, threaded=True)

camera.stop_preview()



 
