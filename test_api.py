import requests
import time
import os

# Configuration
url = "https://mot-recog.facet-cloud.com/recv"
device_id = "2222222"
# Image path
image_path = "C:/Users/thangpv/.gemini/antigravity/brain/c22446bc-9040-41f2-b403-cf1bacd39943/uploaded_image_1767918165811.jpg"

if not os.path.exists(image_path):
    print(f"Error: Image file not found at {image_path}")
    exit(1)

def send_request(desc, data_override, files_override=None):
    print(f"\n--- Testing: {desc} ---")
    
    # Base data
    timestamp = str(int(time.time() * 1000))
    data = {
        'timestamp': timestamp,
        'deviceId': device_id,
        'policeId': "" 
    }
    data.update(data_override)
    
    print(f"Data: {data}")
    
    try:
        with open(image_path, 'rb') as f:
            if files_override:
                files = files_override
            else:
                files = {'image': ('image.jpg', f, 'image/jpeg')}
            
            response = requests.post(url, data=data, files=files)
            print(f"Status Code: {response.status_code}")
            print(f"Body: {response.text}")
    except Exception as e:
        print(f"Error: {e}")

# Test 1: As before
send_request("Base case (empty policeId)", {})

# Test 2: Omit policeId entirely
# Note: 'policeId': None might not work with requests data dict field filtering depending on version, 
# better to construct dict without it.
print(f"\n--- Testing: Omit policeId ---")
try:
    with open(image_path, 'rb') as f:
        timestamp = str(int(time.time() * 1000))
        data = {
            'timestamp': timestamp,
            'deviceId': device_id
        }
        files = {'image': ('image.jpg', f, 'image/jpeg')}
        response = requests.post(url, data=data, files=files)
        print(f"Status Code: {response.status_code}")
        print(f"Body: {response.text}")
except Exception as e:
    print(f"Error: {e}")

# Test 3: Hardcoded Timestamp from user example
send_request("Hardcoded Timestamp", {'timestamp': '1766462090380'})

# Test 4: deviceId as part of URL? No, endpoint is fixed.
# Test 5: Maybe policeId needs to be "null"?
send_request("policeId = 'null'", {'policeId': 'null'})

