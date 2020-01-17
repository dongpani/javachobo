package thread;

public class Ex13_8 {

	public static void main(String[] args) {
		ThreadEx_8_1 th1 = new ThreadEx_8_1();
		ThreadEx_8_2 th2 = new ThreadEx_8_2();
		th1.start(); th2.start(); // 2개 쓰레드 동시에 실행
		
		try {
			th1.sleep(2000);
		}catch(InterruptedException e) {}

		System.out.println("<<main 종료>>"); // 쓰레드가 모두 종료되고 난 후 실행
		
	}
}

class ThreadEx_8_1 extends Thread {
	public void run() {
		for(int i=0; i < 300; i++) System.out.print("-");
		System.out.println("<<th1 종료>>");
	} // run();
}

class ThreadEx_8_2 extends Thread {
	public void run() {
		for(int i=0; i <300; i++) System.out.print("|");
		System.out.println("<<th2 종료>>");
	}
}
