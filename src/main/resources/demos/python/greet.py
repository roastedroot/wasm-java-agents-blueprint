import extism

@extism.plugin_fn
def greet():
    args = extism.input_json()
    extism.output_str(greet_impl(args[0], args[1]))

def greet_impl(name, lang):
    if lang == "fr":
        return f"Bonjour, {name}!"
    elif lang == "de":
        return f"Hallo, {name}!"
    elif lang == "es":
        return f"¡Hola, {name}!"
    else:
        return f"Hello, {name}!"
