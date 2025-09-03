// Language-specific greetings
const GREETINGS = {
    en: "Hello",
    fr: "Bonjour",
    de: "Hallo",
    es: "Hola"
};

function greet(name, lang = 'en') {
    const greeting = GREETINGS[lang] || GREETINGS.en;
    return `${greeting}, ${name}!`;
}
