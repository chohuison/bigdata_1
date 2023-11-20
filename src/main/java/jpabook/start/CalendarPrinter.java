package jpabook.start;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalendarPrinter {
    public static void main(String[] args) {
        // 현재 날짜 정보를 가져옴
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        // 해당 달의 첫 번째 날짜를 가져옴
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

        // 해당 달의 첫 번째 날짜의 요일을 가져옴 (1: 월요일, 2: 화요일, ..., 7: 일요일)
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        // 해당 달의 마지막 날짜를 가져옴
        int lastDayOfMonth = firstDayOfMonth.lengthOfMonth();

        // 일정이 있는 날짜를 설정 (임의로 10일과 20일로 설정)
        int[] eventDays = {10, 20};

        // 달력 출력
        System.out.println("[" + year + "년 " + month + "월" + "]");
        System.out.println("일   월   화   수   목   금   토");

        // 첫 번째 주의 시작 위치 조정
        for (int i = 1; i < firstDayOfWeek; i++) {
            System.out.print("    ");
        }

        // 날짜 출력
        for (int day = 1; day <= lastDayOfMonth; day++) {
            // 일정이 있는 날짜인 경우 0으로 표시
            if (contains(eventDays, day)) {
                System.out.printf("%2d  ", 0);
            } else {
                System.out.printf("%2d  ", day);
            }

            // 토요일마다 줄바꿈
            if ((firstDayOfWeek + day - 1) % 7 == 0) {
                System.out.println();
            }
        }
    }

    // 배열에서 특정 값이 있는지 확인하는 메소드
    public static boolean contains(int[] array, int value) {
        for (int item : array) {
            if (item == value) {
                return true;
            }
        }
        return false;
    }
}
