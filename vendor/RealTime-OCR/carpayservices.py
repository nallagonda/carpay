import requests

def call_verify_image_text(API_URL):
  print(" API_URL                    :   "+ API_URL)

  response = requests.get(API_URL)
  print(response)

  return response.text