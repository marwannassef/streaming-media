import boto3
from flask import Flask
import cv2
import numpy as np
import moviepy.editor as mp
import ffmpeg
import os
import shutil
import json
import requests
import time
import sys

app = Flask(__name__)

@app.route("/")
@app.route('/convertVideo/<video>', methods=['GET'])
def convertVideo(video):
    input_file = 'input.' + video.split('.')[1]
    bucket_Name = 'streaming-video-storage'
    input_dir = 'input'
    output_dir = video.split('.')[0]
    output_file = 'output.m3u8'
    s3 = boto3.client(
        's3',
        aws_access_key_id='AKIA5M3NQV6LPKYNTAWB',
        aws_secret_access_key='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'
    )
    response = s3.list_buckets()

    # Output the bucket names
    print('Existing buckets:')
    for bucket in response['Buckets']:
        print(f'  {bucket["Name"]}')

    s3.download_file(bucket_Name, 'videos/' + video, input_file)
    video_capture = cv2.VideoCapture(input_file)
    height = video_capture.get(cv2.CAP_PROP_FRAME_HEIGHT)
    width = video_capture.get(cv2.CAP_PROP_FRAME_WIDTH)
    qualities = [144]
    if(height >= 240):
        qualities.append(240)
    if(height >= 360):
        qualities.append(360)
    if(height >= 480):
        qualities.append(480)
    if(height >= 720):
        qualities.append(720)
    if(height >= 1080):
        qualities.append(1080)
    if(height >= 1440):
        qualities.append(1440)
    if(height >= 2160):
        qualities.append(2160)

    print(height)
    print(width)
    
    video_capture.release()
    cv2.destroyAllWindows()

    for quality in qualities:
        print(quality)

    if not os.path.exists(input_dir):
        os.makedirs(input_dir)

    for quality in qualities:
        clip = mp.VideoFileClip(input_file)
        # make the height 360px ( According to moviePy documenation The width is then computed so that the width/height ratio is conserved.)
        clip_resized = clip.resize(height=quality)
        clip_resized.write_videofile(input_dir + '/' + str(quality) + '.mp4')

    files = os.listdir(input_dir)
    
    for file in files:
        print(file)

    for file in files:
        fileName = file.split('.')
        input_stream = ffmpeg.input(input_dir + '/' + file)
        if not os.path.exists(output_dir + '/' + fileName[0]):
            os.makedirs(output_dir + '/' + fileName[0])
        output_stream = ffmpeg.output(
            input_stream, output_dir + '/' + fileName[0] + '/' + output_file, format='hls', start_number=0, hls_time=5, hls_list_size=0)
        ffmpeg.run(output_stream)

    f = open(output_dir + '/' + output_file, 'a')
    f.write('#EXTM3U\n#EXT-X-VERSION:5\n\n')
    static_text = '#EXT-X-STREAM-INF:BANDWIDTH={0},CODECS=\"avc1.42c00d,mp4a.40.2\",RESOLUTION={1}\n{2}/output.m3u8\n\n'
    for quality in qualities:
        if(quality == 144):
            f.write(static_text.format('100000', '256x144', '144'))
            files = os.listdir(output_dir + '/144/')
            for file in files:
                s3.upload_file(output_dir + '/144/' + file,
                               bucket_Name, 'convertedVideos/' + video.split('.')[0] + '/144/' + file)
        if(quality == 240):
            f.write(static_text.format('200000', '426x240', '240'))
            files = os.listdir(output_dir + '/240/')
            for file in files:
                s3.upload_file(output_dir + '/240/' + file,
                               bucket_Name, 'convertedVideos/' + video.split('.')[0] + '/240/' + file)
        if(quality == 360):
            f.write(static_text.format('300000', '640x360', '360'))
            files = os.listdir(output_dir + '/360/')
            for file in files:
                s3.upload_file(output_dir + '/360/' + file,
                               bucket_Name, 'convertedVideos/' + video.split('.')[0] + '/360/' + file)
        if(quality == 480):
            f.write(static_text.format('400000', '854x480', '480'))
            files = os.listdir(output_dir + '/480/')
            for file in files:
                s3.upload_file(output_dir + '/480/' + file,
                               bucket_Name, 'convertedVideos/' + video.split('.')[0] + '/480/' + file)
        if(quality == 720):
            f.write(static_text.format('500000', '1280x720', '720'))
            files = os.listdir(output_dir + '/720/')
            for file in files:
                s3.upload_file(output_dir + '/720/' + file,
                               bucket_Name, 'convertedVideos/' + video.split('.')[0] + '/720/' + file)
        if(quality == 1080):
            f.write(static_text.format('600000', '1920x1080', '1080'))
            files = os.listdir(output_dir + '/1080/')
            for file in files:
                s3.upload_file(output_dir + '/1080/' + file,
                               bucket_Name, 'convertedVideos/' + video.split('.')[0] + '/1080/' + file)
        if(quality == 1440):
            f.write(static_text.format('700000', '2560x1440', '1440'))
            files = os.listdir(output_dir + '/1440/')
            for file in files:
                s3.upload_file(output_dir + '/1440/' + file,
                               bucket_Name, 'convertedVideos/' + video.split('.')[0] + '/1440/' + file)
        if(quality == 2160):
            f.write(static_text.format('800000', '3840x2160', '2160'))
            files = os.listdir(output_dir + '/2160/')
            for file in files:
                s3.upload_file(output_dir + '/2160/' + file,
                               bucket_Name, 'convertedVideos/' + video.split('.')[0] + '/2160/' + file)

    f.close()
    s3.upload_file(output_dir + '/' + output_file, bucket_Name,
                   'convertedVideos/' + video.split('.')[0] + '/' + output_file)


    shutil.rmtree(input_dir)
    shutil.rmtree(output_dir)
    os.remove(input_file)

    headers = {'Content-Type': 'application/json'}
    api_url = 'http://172.19.140.115:8081/video/activate/' + output_dir
    response = requests.put(api_url, headers=headers)

    return "Done!"
    
@app.route('/test/<video>', methods=['GET'])
def test(video):
    print(video)
    #headers = {'Content-Type': 'application/json'}
           #'Authorization': 'Bearer {0}'.format(api_token)}
    #api_url = 'http://172.19.140.115:8081/video/activate/' + 'asdsad'
    #response = requests.put(api_url, headers=headers)
    #if response.status_code == 200:
    #    print(json.loads(response.content.decode('utf-8')))
    #else:
    #    print('Error')
    return "Done!"
    
@app.route('/detectVideoObjects/<video>', methods=['GET'])
def detectVideoObjects(video):
    print(video)
    objects = dict()

    CONFIDENCE = 0.5
    SCORE_THRESHOLD = 0.5
    IOU_THRESHOLD = 0.5
    config_path = "cfg/yolov3.cfg"
    weights_path = "weights/yolov3.weights"
    font_scale = 1
    thickness = 1
    labels = open("data/coco.names").read().strip().split("\n")
    colors = np.random.randint(0, 255, size=(len(labels), 3), dtype="uint8")

    net = cv2.dnn.readNetFromDarknet(config_path, weights_path)

    ln = net.getLayerNames()
    ln = [ln[i[0] - 1] for i in net.getUnconnectedOutLayers()]
    # read the file from the command line
    # video_file = sys.argv[1]
    input_file = 'input.' + video.split('.')[1]
    #os.remove(input_file)
    #os.remove("output.avi")
    bucket_Name = 'streaming-video-storage'
    s3 = boto3.client(
        's3',
        aws_access_key_id='AKIA5M3NQV6LPKYNTAWB',
        aws_secret_access_key='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'
    )
    response = s3.list_buckets()

    # Output the bucket names
    print('Existing buckets:')
    for bucket in response['Buckets']:
        print(f'  {bucket["Name"]}')

    s3.download_file(bucket_Name, 'videos/' + video, input_file)

    cap = cv2.VideoCapture(input_file)
    _, image = cap.read()
    h, w = image.shape[:2]
    fourcc = cv2.VideoWriter_fourcc(*"XVID")
    out = cv2.VideoWriter("output.avi", fourcc, 20.0, (w, h))
    while True:
        _, image = cap.read()

        h, w = image.shape[:2]
        blob = cv2.dnn.blobFromImage(image, 1/255.0, (416, 416), swapRB=True, crop=False)
        net.setInput(blob)
        start = time.perf_counter()
        layer_outputs = net.forward(ln)
        time_took = time.perf_counter() - start
        print("Time took:", time_took)
        boxes, confidences, class_ids = [], [], []

        # loop over each of the layer outputs
        for output in layer_outputs:
            # loop over each of the object detections
            for detection in output:
                # extract the class id (label) and confidence (as a probability) of
                # the current object detection
                scores = detection[5:]
                class_id = np.argmax(scores)
                confidence = scores[class_id]
                # discard weak predictions by ensuring the detected
                # probability is greater than the minimum probability
                if confidence > CONFIDENCE:
                    # scale the bounding box coordinates back relative to the
                    # size of the image, keeping in mind that YOLO actually
                    # returns the center (x, y)-coordinates of the bounding
                    # box followed by the boxes' width and height
                    box = detection[:4] * np.array([w, h, w, h])
                    (centerX, centerY, width, height) = box.astype("int")

                    # use the center (x, y)-coordinates to derive the top and
                    # and left corner of the bounding box
                    x = int(centerX - (width / 2))
                    y = int(centerY - (height / 2))

                    # update our list of bounding box coordinates, confidences,
                    # and class IDs
                    boxes.append([x, y, int(width), int(height)])
                    confidences.append(float(confidence))
                    class_ids.append(class_id)

        # perform the non maximum suppression given the scores defined before
        idxs = cv2.dnn.NMSBoxes(boxes, confidences, SCORE_THRESHOLD, IOU_THRESHOLD)

        font_scale = 1
        thickness = 1

        # ensure at least one detection exists
        if len(idxs) > 0:
            # loop over the indexes we are keeping
            for i in idxs.flatten():
                # extract the bounding box coordinates
                x, y = boxes[i][0], boxes[i][1]
                w, h = boxes[i][2], boxes[i][3]
                # draw a bounding box rectangle and label on the image
                color = [int(c) for c in colors[class_ids[i]]]
                cv2.rectangle(image, (x, y), (x + w, y + h), color=color, thickness=thickness)
                text = f"{labels[class_ids[i]]}: {confidences[i]:.2f}"
                #print(text)
                if text not in objects:
                    objects[text] = 1
                else:
                    #print(objects)
                    objects[text] = objects[text] + 1
                # calculate text width & height to draw the transparent boxes as background of the text
                (text_width, text_height) = cv2.getTextSize(text, cv2.FONT_HERSHEY_SIMPLEX, fontScale=font_scale, thickness=thickness)[0]
                text_offset_x = x
                text_offset_y = y - 5
                box_coords = ((text_offset_x, text_offset_y), (text_offset_x + text_width + 2, text_offset_y - text_height))
                overlay = image.copy()
                cv2.rectangle(overlay, box_coords[0], box_coords[1], color=color, thickness=cv2.FILLED)
                # add opacity (transparency to the box)
                image = cv2.addWeighted(overlay, 0.6, image, 0.4, 0)
                # now put the text (label: confidence %)
                cv2.putText(image, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX,
                    fontScale=font_scale, color=(0, 0, 0), thickness=thickness)

        out.write(image)
        cv2.imshow("image", image)
        
        if ord("q") == cv2.waitKey(1):
            break


    print(objects)
    cap.release()
    cv2.destroyAllWindows()


if __name__ == "__main__":
    app.run(host= '0.0.0.0')
