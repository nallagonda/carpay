import asyncio
import requests

def sei_blockchain_create(file, name, licnum, phone):
  print(" file_name                    :   "+ file.filename)
  print(" name                         :   "+ name)
  print(" licnum                       :   "+ licnum)
  print(" phone                        :   "+ phone)

  bc_token = "sei_blockchain_token"

  # Call NodeJS API for Block Chain and get Owner Token

  # Save to mongoDB 



  # Call Notification with Token send_push_notification()

  
  return bc_token

def verify_image_text_service(image_text):
  print(" image_text                   :   "+ image_text)

  # SEI BlockChain call 

  sei_blockchain_licnum = 'SKN1010'

  if image_text == sei_blockchain_licnum:
    print("Both strings are equal")
    is_match =  "Y"
  else:
    print("Both strings are NOT equal")
    is_match =  "N"

  return is_match

def sei_blockchain_create_test(name, licnum, phone):
  print(" name                         :   "+ name)
  print(" licnum                       :   "+ licnum)
  print(" phone                        :   "+ phone)

  bc_token = "sei_blockchain_token"

  # Call NodeJS API for Block Chain and get Owner Token

  # Save to mongoDB 

  

  # Call Notification with Token send_push_notification()

  
  return bc_token



def send_authorization_approval(owner_token, is_authorized):

  API_URL = "http://localhost:9013/api/v1/send_authorization_approval?owner_token="+owner_token.strip()

  print(" API_URL                    :   "+ API_URL)

  response = requests.get(API_URL)

  print("After POS Successfull !"+str(response))

  return response.text
