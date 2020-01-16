package thread;

public class Ex13_1 {

	public static void main(String[] args) {
		ThreadEx1_1 t1 = new ThreadEx1_1();
		
		Runnable r = new ThreadEx1_2();
		Thread t2 = new Thread(r); // 생성자 쓰레드(러너블 타켓)
		
		t1.start();
		t2.start();		
	}
}

class ThreadEx1_1 extends Thread {
	public void run() {
		for(int i=0; i < 5; i++) {
			System.out.println(getName()); // 조상인 쓰레드의 getName()을 호출.
		}
	}
}

class ThreadEx1_2 implements Runnable {
	@Override
	public void run() {
		for(int i=0; i < 5; i++) {
			System.out.println(Thread.currentThread().getName());
		}
	}

}

// 쓰레드가 2개 있다는 이야기.

