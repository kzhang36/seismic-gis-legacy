# *

import requests
import json
import logging


logging.basicConfig(level=logging.DEBUG,
                    filename='log.txt',
                    encoding='UTF-8',
                    format='%(asctime)s - %(filename)s[line:%(lineno)d] - %(levelname)s: %(message)s')

gp_object = {
    'did': '',
    'depth': '',
    'lon': '',
    'lat': '',
    'location': '',
    'scale': '',
    'time': ''
}
result = []
index = 1

def get_data(page=1):
    url = 'https://www.ceic.ac.cn/ajax/speedsearch'
    params = {'num': '1', 'page': page}

    # 请求
    response = requests.get(url, params)
    if response.status_code != 200:
        return logging.error("无法获取数据！！！！！")

    # 去掉前后的多余的括号
    text = response.text.split('(')[1].split(')')[0]
    print(text)

    # 字符串转换成 json 对象
    json_object = json.loads(text)
    result.append(json_object)

    if json_object['page'] and json_object['page'] > index:
        index += 1
        get_data(index)


    return result


def set_data(gp):
    url = 'http://127.0.0.1:8089/gp'
    headers = {'Content-Type': 'application/json'}
    res = requests.post(url, data=gp, headers=headers)

    if res.status_code == 200:
        logging.info("后端传输成功"+str(gp))


if __name__ == '__main__':
    r = get_data()
    print(r)

    for j in r:
        for item in j['shuju']:
            gp_object['depth'] = item['EPI_DEPTH']
            gp_object['lon'] = item['EPI_LON']
            gp_object['lat'] = item['EPI_LAT']
            gp_object['scale'] = item['M']
            gp_object['location'] = item['LOCATION_C']
            gp_object['time'] = item['O_TIME']
            gp_object['did'] = item['NEW_DID']

            # set_data(json.dumps(gp_object))
