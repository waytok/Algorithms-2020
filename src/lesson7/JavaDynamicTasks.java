package lesson7;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     *
     * сложность O(n*m)
     * память S(n*m)
     */
    public static String longestCommonSubSequence(String first, String second) {
        int currentLength;
        StringBuilder output = new StringBuilder();
        int[][] matchingChars = new int[first.length()][second.length()];
        matchingChars[0][0] = (first.charAt(0) == second.charAt(0)) ? 1 : 0;

        for (int i = 1; i < first.length(); i++)
            matchingChars[i][0] = (first.charAt(i) == second.charAt(0) || matchingChars[i - 1][0] == 1) ? 1 : 0;
        for (int j = 1; j < second.length(); j++)
            matchingChars[0][j] = (first.charAt(0) == second.charAt(j) || matchingChars[0][j - 1] == 1) ? 1 : 0;
        for (int i = 1; i < first.length(); i++) {
            for (int j = 1; j < second.length(); j++) {
                matchingChars[i][j] = (first.charAt(i) == second.charAt(j)) ? matchingChars[i-1][j-1] + 1 :
                        Integer.max(matchingChars[i-1][j], matchingChars[i][j-1]);
            }
        }

        int i = first.length() - 1;
        int j = second.length() - 1;

        while (i > 0 && j > 0 && matchingChars[i][j] != 0) {
            if (matchingChars[i][j-1] == matchingChars[i][j]) j--;
            else if (matchingChars[i-1][j] == matchingChars[i][j]) i--;
            else {
                output.append(first.charAt(i));
                i--;
                j--;
            }
        }

        if (j == 0) while (i>0 && matchingChars[i-1][j] == matchingChars[i][j]) i--;
        if (i == 0) while (j>0 && matchingChars[i][j-1] == matchingChars[i][j]) j--;

        if (first.charAt(i) == second.charAt(j)) output.append(first.charAt(i));

        return output.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     *
     * сложность O(n*log(n))
     * память S(n)
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() <= 1) return list;

        int[] indexes = new int[list.size()];
        int[] maxLength = new int[list.size()];
        int[] tempRes = new int[list.size()];
        int length = 0;

        List<Integer> sequence = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) tempRes[i] = list.get(i);
            else if (list.get(i) > tempRes[length]) {
                length++;
                indexes[i] = length;
                tempRes[length] = list.get(i);
            } else if (list.get(i) < tempRes[0]) {
                indexes[i] = 0;
                tempRes[0] = list.get(i);
            }
            else {
                int begin = 0;
                int end = length;
                int index = (end + begin) / 2;
                while (begin < end) {
                    index = (end + begin) / 2;
                    if (list.get(i) >= tempRes[index]) {
                        index++;
                        begin = index;
                    }
                    else end = index;
                }
                tempRes[index] = list.get(i);
                indexes[i] = index;
            }
            maxLength[i] = length;
        }

        int lastAdded = Integer.MAX_VALUE;
        int elementToAdd = lastAdded;
        int addingIndex = list.size() - 1;

        for (int i = list.size() - 1; i >= 0; i--) if (indexes[i] == length) {
            if (list.get(i) <= lastAdded) {
                elementToAdd = list.get(i);
                addingIndex = i;
            }
            if (i == 0 || maxLength[i] != maxLength[i - 1]) {
                sequence.add(0, elementToAdd);
                lastAdded = elementToAdd;
                i = addingIndex;
                length--;
            }
        }


        return sequence;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
