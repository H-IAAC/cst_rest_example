import sys
from flask import Flask, request, Response
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM
import wikipediaapi

app = Flask(__name__)

translate_tokenizer = AutoTokenizer.from_pretrained("wandemberg-eld/opus-mt-en-de-finetuned-en-to-de")
translate_model = model = AutoModelForSeq2SeqLM.from_pretrained("wandemberg-eld/opus-mt-en-de-finetuned-en-to-de")
wiki_wiki = wikipediaapi.Wikipedia('en')

@app.route('/')
def home():
    return "API."

@app.route('/translate/', methods=['POST'])
def translate():
    
    try: 
        request_data = request.get_json()
    except:
        request_data = request.form
    
    en_text = list(request_data['en_text'])


    tokenized = translate_tokenizer.prepare_seq2seq_batch(en_text, return_tensors="pt")["input_ids"]
    german_translation = translate_model.generate(tokenized)

    german_text = translate_tokenizer.batch_decode(german_translation, skip_special_tokens=True)

    return german_text


@app.route('/whatis/<topic>')
def wikipedia(topic : str):
    page = wiki_wiki.page(topic)

    if page.exists():
        msg = page.title + "\n\n"

        msg += page.summary[0:1000]
    else:
        msg = "sorry, but I couldn't find a page on wikipedia about it"

    return msg


def split(string): 
    li = list(string.split(":")) 
    return li 



if __name__ == "__main__":
    args = sys.argv[1:]
    HOST = split(args[0])[0]
    PORT = int(split(args[0])[1])
    app.run(debug=True, host=HOST, port=PORT)