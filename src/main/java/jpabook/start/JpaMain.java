package jpabook.start;

import jpabook.start.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Scanner;

public class JpaMain {
     static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();


        try {
            tx.begin();

            //             사전 데이터
//            게스트 4명, 호스트 4명 계정 미리 만들기
            Member guest1 = new Member("김손님", RoleType.GUEST);
            em.persist(guest1);

            Member guest2 = new Member("이손님", RoleType.GUEST);
            em.persist(guest2);

            Member guest3 = new Member("박손님", RoleType.GUEST);
            em.persist(guest3);

            Member guest4 = new Member("서손님", RoleType.GUEST);
            em.persist(guest4);


            Member host1 = new Member("김주인", RoleType.HOST);
            em.persist(host1);

            Member host2 = new Member("이주인", RoleType.HOST);
            em.persist(host2);

            Member host3 = new Member("박주인", RoleType.HOST);
            em.persist(host3);

            Member host4 = new Member("서주인", RoleType.HOST);
            em.persist(host4);


            // 숙소 3건(전체 2건, 개인실 1건 이상)

            // 후기 등록 - 숙소별 최소 3건

            while(true)
            {
                System.out.println("검사항목번호를 입력하세요(1~10, 종료는 0) : ");
                Scanner scanner = new Scanner(System.in);
                int input = scanner.nextInt();

                if(input == 0)
                    break;

                switch (input)
                {
                    case 1:
                        Part1.registHouse();
                        break;
                    default:
                        break;
                }
            }

            em.flush();
            em.clear();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

    }
}
