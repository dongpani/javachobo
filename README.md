# javachobo
자바의 정석 예제 연습 및 정리

<br><br>

## 쓰레드
쓰레드는  프로세스를 의미한다. 하나의 어플리케이션이 실행되면, OS자원을 할당받아 프로세스에 등록이 되는데, 이 때 반드시 하나의 쓰레드(싱글쓰레드)를 구현해야 한다.


```
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


/*
결과
	Thread-0
	Thread-0
	Thread-0
	Thread-0
	Thread-0
	Thread-1
	Thread-1
	Thread-1
	Thread-1
	Thread-1
*/

```

- 쓰레드를 실행시키려면 반드시 start() 메서드를 실행해야한다. (그 전까지는 대기상태) 
- 물론, 쓰레드가 하나도 없다면 자동으로 실행된다.

<br><br>

## 멀티쓰레드와 싱글쓰레드

싱글쓰레드는 하나의 작업이 끝나야 다음 작업을 수행할 수 있지만, 멀티쓰레드는 동시에 작업을 시작할 수 있다.


### 싱글쓰레드


```
package thread;

import javax.swing.JOptionPane;

public class Ex13_4 {

	public static void main(String[] args) {
		String input = JOptionPane.showInputDialog("아무 값이나 입력하세요.");
		System.out.println("입력하신 값은 " + input + "입니다.");
		
		for(int i=10; i>0; i--) {
			System.out.println(i);
			try {
				Thread.sleep(1000);
			} catch(Exception e) {}
		}
	}
}

/*
결과

입력하신 값은 sdfs입니다.
10
9
8
7
6
5
4
3
2
1
*/

```

- 값을 입력 한 후에 쓰레드가 실행된다.


<br>


### 멀티쓰레드


```
package thread;

import javax.swing.JOptionPane;

public class Ex13_5 {

	public static void main(String[] args) throws Exception {
		ThreadEx5_1 th1 = new ThreadEx5_1();
		th1.start();
		
		String input = JOptionPane.showInputDialog("아무 값이나 입력하세요.");
		System.out.println("입력하신 값은 " + input + "입니다.");
	}

}


class ThreadEx5_1 extends Thread {
	public void run() {
		for(int i=10; i > 0; i--) {
			System.out.println(i);
			try {
				sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}


/*
10
9
8
입력하신 값은 sdfsdf입니다.
7
6
5
4
3
2
1
*/
```

- main 쓰레드와 함께 실행되므로, 동시에 메서드가 실행된다.


<br><br>


### 쓰레드의 우선순위

쓰레드 실행의 우선순위를 지정할 수 있다. main 메서드의 우선순위는 5이다.


```
package thread;

public class Ex13_6 {

	public static void main(String[] args) {
		ThreadEx6_1 th1 = new ThreadEx6_1();
		ThreadEx6_2 th2 = new ThreadEx6_2();
		
		th2.setPriority(7);
		
		System.out.println("Priority of th1(-) : " + th1.getPriority());
		System.out.println("Priority of th2(|) : " + th2.getPriority());
		th1.start();
		th2.start();
	}

}

class ThreadEx6_1 extends Thread {
	public void run() {
		for(int i=0; i<300; i++) {
			System.out.print("-");
			for(int x=0; x <10000000; x++);
		}
	}
}

class ThreadEx6_2 extends Thread {
	public void run() {
		for(int i=0; i < 300; i++) {
			System.out.print("|");
			for(int x=0; x <10000000; x++);
		}
	}
}


/*
Priority of th1(-) : 5
Priority of th2(|) : 7
-|-|---------------------------------------------------------------------------------------------------------|||||||||||||||||||--|||||||||.. 계속..
*/

```


