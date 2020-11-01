package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import static java.lang.Math.sqrt;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     *
     * сложность O(m*n)
     * память S(1)
     */
    static public String longestCommonSubstring(String first, String second) {
        int firstLetter = -1;
        int maxLength = 0;
        int currentLength;
        // Рассматриваем элементы в таблице по диагоналям, запоминая длину наибольшей общей подстроки и индекс ее первого элемента.
        // Сначала рассматриваем главную диагональ и диагонали ниже.
        // Если j+i-й элемент второй последовательности равен i элементу первой, увеличиваем длину общей подстроки на 1.
        // Если элементы не равны, приравниваем нулю длину рассматриваемой общей подстроки.
        for (int i = 0; i < second.length(); i++) {
            currentLength = 0;
            for (int j = 0; j+i < second.length() && j < first.length(); j++)
                if (first.charAt(j) == second.charAt(i + j)) {
                    currentLength += 1;
                    if (currentLength == maxLength && firstLetter > j - maxLength + 1)
                        firstLetter = j - maxLength + 1;
                    if (currentLength > maxLength) {
                        maxLength = currentLength;
                        firstLetter = j - maxLength + 1;
                    }
                } else currentLength = 0;
        }
        // Аналогично рассматриваем диагонали выше главной, индекс символа в первой строке j+i, во второй - j.
        for (int i = 1; i < first.length(); i++) {
            currentLength = 0;
            for (int j = 0; j+i < first.length() && j < second.length(); j++)
                if (first.charAt(i + j) == second.charAt(j)) {
                    currentLength += 1;
                    if (currentLength == maxLength && firstLetter > i + j - maxLength + 1)
                        firstLetter = i + j - maxLength + 1;
                    if (currentLength > maxLength) {
                        maxLength = currentLength;
                        firstLetter = i + j - maxLength + 1;
                    }
                } else currentLength = 0;
        }
        if (firstLetter != -1) return(first.substring(firstLetter, firstLetter + maxLength));
        else return "";
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     *
     * сложность O(n*log(log(n)))
     * память S(n)
     */
    static public int calcPrimesNumber(int limit) {
        if (limit <=1) return 0;
        boolean[] notPrime = new boolean[limit];
        int numberOfPrimes = 0;
        notPrime[0] = true;
        for (int i = 1; i <= sqrt(limit); i++) {
            if (!notPrime[i-1]) {
                int j = i;
                while (i*j <= limit) {
                    notPrime[i*j-1] = true;
                    j++;
                }
            }
        }
        for (int i = 0; i < limit; i++) if (!notPrime[i]) numberOfPrimes +=1;
        return numberOfPrimes;
    }
}
