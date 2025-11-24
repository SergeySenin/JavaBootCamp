package org.study.bootcamp.task_5.reverse_array.v2.easy;

public class Main {
    static class Example {
        public static void reverse(int[] nums) {
            for (int i = 0; i < nums.length / 2; i++) {
                int temp = nums[i];
                nums[i] = nums[nums.length - 1 - i];
                nums[nums.length - 1 - i] = temp;
            }
        }

        public static void main(String[] args) {
            int[] numbers = {1, 2, 3, 4, 5};

            System.out.println("До разворота:");
            for (int num : numbers) {
                System.out.print(num + " ");
            }

            Example.reverse(numbers);

            System.out.println("\nПосле разворота:");
            for (int num : numbers) {
                System.out.print(num + " ");
            }
        }
    }
}



/*
Разберём последовательно каждую итерацию цикла для массива [1, 2, 3, 4, 5]:

Почему цикл останавливается?
Условие: i < nums.length / 2 → i < 2.5
i = 0 → 0 < 2.5 ✓
i = 1 → 1 < 2.5 ✓
i = 2 → 2 < 2.5 ✓
i = 3 → 3 < 2.5 ✗ (цикл завершается)

Визуализация обмена:
text
Итерация 0: обмениваем крайние элементы
[1, 2, 3, 4, 5] → [5, 2, 3, 4, 1]

Итерация 1: обмениваем вторые с краёв
[5, 2, 3, 4, 1] → [5, 4, 3, 2, 1]

Итерация 2: центральный элемент (остаётся на месте)
[5, 4, 3, 2, 1] → [5, 4, 3, 2, 1]

Разбираем по частям:
java
nums[nums.length - 1 - i]
nums - это наш массив (например [1,2,3,4,5])
[ ] - оператор доступа к элементу массива по индексу
nums.length - длина массива (для [1,2,3,4,5] это 5)
nums.length - 1 - индекс последнего элемента (5 - 1 = 4)
nums.length - 1 - i - индекс элемента с конца, отступая на i позиций
 */
