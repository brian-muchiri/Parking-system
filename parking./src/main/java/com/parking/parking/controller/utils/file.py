# works with both python 2 and 3
from __future__ import print_function

import africastalking

class SMS:
    def __init__(self):
        print("jhgf")
		# Set your app credentials
        self.api_key = "d09ecd20ee7af3a67e9058ea65295199d040765dd08bb9754629fde8f6de6f26"
        self.username = "smatr"

        # Initialize the SDK
        africastalking.initialize(self.username, self.api_key)

        # Get the SMS service
        self.sms = africastalking.SMS

    def send(self):
            recipients = ["+254798576482","+254745279180" , "+254704847676","+254793865366","+254768315754"]
            auth_code = ''
            time =" time+30"
            parking_slot="name"
            message = f"Dear Customer your have successfully reserved a parking slot at {parking_slot}, \n Provide this auth code {auth_code} on your arrival, Your slot will be de-allocated  at {time}"
            sender = "smatr parking thing"
            try:
                response = self.sms.send(message, recipients)
                print (response)
            except Exception as e:
                print ('Encountered an error while sending: %s' % str(e))

if __name__ == '__main__':
    SMS().send()