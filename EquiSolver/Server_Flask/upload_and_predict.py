import cv2
import numpy
from keras.datasets import mnist
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import Dropout
from keras.layers import Flatten
from keras.layers.convolutional import Conv2D
from keras.layers.convolutional import MaxPooling2D
from keras.utils import np_utils
from keras import backend as K
# K.set_image_dim_ordering('th')
from keras.models import model_from_json
from flask import Flask, jsonify, request




import cv2
import numpy as np

def loaclize(image_object):
    if image_object is not None:
        # images.append(img)
        image_object = ~image_object
        x, threshold_variable = cv2.threshold(image_object, 127, 255, cv2.THRESH_BINARY)
        contours, x = cv2.findContours(threshold_variable, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
        sorted_contours = sorted(contours, key=lambda ctr: cv2.boundingRect(ctr)[0])
        height = int(28)
        width = int(28)
        data_train = []
        # print(len(cnt))
        bounding_rectangles = []
        for c in sorted_contours:
            x, y, width, height = cv2.boundingRect(c)
            rect = [x, y, width, height]
            bounding_rectangles.append(rect)
        # print(rects)
        boolean_rectangles = []
        for rectg in bounding_rectangles:
            l = []
            for rectangle in bounding_rectangles:
                count = 0
                if rectangle != rectg:
                    if rectg[0] < (rectangle[0] + rectangle[2] + 10) and rectangle[0] < (rectg[0] + rectg[2] + 10) and rectg[1] < (rectangle[1] + rectangle[3] + 10) and \
                            rectangle[1] < (rectg[1] + rectg[3] + 10):
                        count = 1
                    l.append(count)
                if rectangle == rectg:
                    l.append(0)
            boolean_rectangles.append(l)
        # print(bool_rect)
        dump_rectangle = []
        for count1 in range(0, len(sorted_contours)):
            for count2 in range(0, len(sorted_contours)):
                if boolean_rectangles[count1][count2] == 1:
                    area1 = bounding_rectangles[count1][2] * bounding_rectangles[count1][3]
                    area2 = bounding_rectangles[count2][2] * bounding_rectangles[count2][3]
                    if (area1 == min(area1, area2)):
                        dump_rectangle.append(bounding_rectangles[count1])
        # print(len(dump_rect))
        rectangle_final = [i for i in bounding_rectangles if i not in dump_rectangle]
        # print(final_rect)
        for rectg in rectangle_final:
            x = rectg[0]
            y = rectg[1]
            width = rectg[2]
            height = rectg[3]
            im_crop = threshold_variable[y:y + height + 10, x:x + width + 10]

            im_resize = cv2.resize(im_crop, (28, 28))
            cv2.imshow("work", im_resize)
            cv2.waitKey(0)
            cv2.destroyAllWindows()

            im_resize = np.reshape(im_resize, (1, 28, 28))
            data_train.append(im_resize)
    return data_train




app = Flask(__name__)

@app.route('/predict', methods = ['POST'])
def predict():
    imagefile = request.files['image']
    imagefile.save('new.jpeg')
    img = cv2.imread('new.jpeg', cv2.IMREAD_GRAYSCALE)
    img = cv2.resize(img, (200, 200), interpolation=cv2.INTER_AREA)
    # cv2.imshow("dum", img)
    img = cv2.imread('test.jpg', cv2.IMREAD_GRAYSCALE)
    # img = cv2.imread('testimage1.jpeg', cv2.IMREAD_GRAYSCALE)
    train_data = loaclize(img)
    str_equation=''
    result_to_operator = dict()
    for i in range(10):
        result_to_operator[i] = str(i)
    result_to_operator[10] = '-'
    result_to_operator[11] = '+'
    result_to_operator[12] = '*'
    result_to_operator[13] = '/'
    for i in range(len(train_data)):
        train_data[i]=np.array(train_data[i])
        train_data[i]=train_data[i].reshape(1,1,28,28)
        equation=loaded_model.predict_classes(train_data[i])

        str_equation += result_to_operator[equation[0]]
    try:
        return str((str_equation, eval(str_equation)))
    except:
        return str((str_equation, "Incorrect Equation"))


with open('final_trained_model.json', 'r') as f:
    loaded_model = model_from_json(f.read())

loaded_model.load_weights("model_weights.h5")


if __name__ == '__main__':
    app.run(host='127.0.0.1',debug=False,threaded = False,port = 6000)
