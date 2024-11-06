Проєкт реалізує криптоаналізатор для класичного шифру Цезаря (без кодування спецсимволів), що дозволяє шифрувати та розшифровувати тексти англійською та українською мовами з вказаним ключем, а також підбирати ключ до зашифрованих файлів аналізуючи частоту символів у варіантах з різними ключами. Успішно реалізовано шифрування, розшифрування та підбір ключа з автоматичним визначенням мови зашифрованого тексту (українська/англійська) та використання відповідного словника за замовчуванням, у разі відсутнього аргументу шляху до файлу для текстового аналізу. Також було розроблено клас CLI для роботи з програмою, якщо вона запущена без аргументів. Не вдалось розібратись як зібрати jar, вказавши опцію для кодування, щоб при роботі з програмою через командний рядок не вказувати щоразу опцію -Dfile.encoding=UTF-8 для коректного відображення кирилиці та правильного сприйняття програмою шляхів до файлів з кириличними символами, якщо вони передаються в режимі роботи через CLI. При перевірці хотілось би, щоб ви звернули увагу на обробку та прокидування вийнятків, адже суттєво кращого рішення не вдалось придумати, та загальний підхід до реалізації проєкту.