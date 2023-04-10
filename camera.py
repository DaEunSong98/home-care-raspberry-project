import time
import io
import threading
import picamera


class Camera(object):
    thread_ = None
    frame__ = None
    lastAccessed = 0

    def initialize(self):
        if Camera.thread_ is None:
            Camera.thread_ = threading.Thread(target=self._thread)
            Camera.thread_.start()

            while self.frame__ is None:
                time.sleep(0)

    def get_frame(self):
        Camera.lastAccessed = time.time()
        self.initialize()
        return self.frame__

    @classmethod
    def _thread(cls):
        with picamera.PiCamera() as camera:
            camera.resolution = (320, 240)
            camera.hflip = True
            camera.vflip = True

            camera.start_preview()
            time.sleep(2)

            stream = io.BytesIO()
            for foo in camera.capture_continuous(stream, 'jpeg',
                                                 use_video_port=True):
                stream.seek(0)
                cls.frame__ = stream.read()

                stream.seek(0)
                stream.truncate()

                if time.time() - cls.lastAccessed > 10:
                    break
        cls.thread = None
