package jpabook.start.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ConvenienceSetting {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void convenienceSetting() {

        EntityManager em = emf.createEntityManager();

        // 트랜잭션 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 기본 편의시설
            Convenience default1 = new Convenience("화장지", ConvenienceType.DEFAULT);
            em.persist(default1);
            Convenience default2 = new Convenience("손과 몸을 씻을 수 있는 비누", ConvenienceType.DEFAULT);
            em.persist(default2);
            Convenience defalut3 = new Convenience("게스트당 수건 1장",  ConvenienceType.DEFAULT);
            em.persist(defalut3);
            Convenience default4 = new Convenience("침대당 침구 1세트", ConvenienceType.DEFAULT);
            em.persist(default4);
            Convenience default5 = new Convenience("게스트당 베개 1개", ConvenienceType.DEFAULT);
            em.persist(default5);
            Convenience default6 = new Convenience("청소용품", ConvenienceType.DEFAULT);
            em.persist(default6);

            // 게스트가 가장 많이 검색하는 편의시설
            Convenience guest1 = new Convenience("수영장", ConvenienceType.GUEST);
            em.persist(guest1);
            Convenience guest2 = new Convenience("와이파이", ConvenienceType.GUEST);
            em.persist(guest2);
            Convenience guest3 = new Convenience("주방", ConvenienceType.GUEST);
            em.persist(guest3);
            Convenience guest4 = new Convenience("무료 주차 공간", ConvenienceType.GUEST);
            em.persist(guest4);
            Convenience guest5 = new Convenience("자쿠지", ConvenienceType.GUEST);
            em.persist(guest5);
            Convenience guest6 = new Convenience("세탁기 또는 건조기", ConvenienceType.GUEST);
            em.persist(guest6);
            Convenience guest7 = new Convenience("에어컨 또는 난방", ConvenienceType.GUEST);
            em.persist(guest7);
            Convenience guest8 = new Convenience("셀프 체크인", ConvenienceType.GUEST);
            em.persist(guest8);
            Convenience guest9 = new Convenience("노트북 작업 공간", ConvenienceType.GUEST);
            em.persist(guest9);
            Convenience guest10 = new Convenience("반려동물 동반 가능", ConvenienceType.GUEST);
            em.persist(guest10);
            // 안전 편의시설
            Convenience safety1 = new Convenience("일산화탄소 경보기", ConvenienceType.SAFETY);
            em.persist(safety1);
            Convenience safety2 = new Convenience("화재 경보기", ConvenienceType.SAFETY);
            em.persist(safety2);
            Convenience safety3  = new Convenience("소화기", ConvenienceType.SAFETY);
            em.persist(safety3);
            Convenience safety4  = new Convenience("구급상자", ConvenienceType.SAFETY);
            em.persist(safety4);
            Convenience safety5  = new Convenience("비상 대피 안내도 및 현지 응급 구조기관 번호", ConvenienceType.SAFETY);
            em.persist(safety5);

            // 접근성 편의시설
            Convenience accessibility1  = new Convenience("계단이나 단차가 없는 현관", ConvenienceType.ACCESSIBILITY);
            em.persist(accessibility1);
            Convenience accessibility2  = new Convenience("폭 32인치/81CM 이상의 넓은 출입구", ConvenienceType.ACCESSIBILITY);
            em.persist(accessibility2);
            Convenience accessibility3  = new Convenience("폭 36인치/91CM 이상의 넓은 복도", ConvenienceType.ACCESSIBILITY);
            em.persist(accessibility3);
            Convenience accessibility4  = new Convenience("휠체어 접근 가능 욕실", ConvenienceType.ACCESSIBILITY);
            em.persist(accessibility4);

            tx.commit();

        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
