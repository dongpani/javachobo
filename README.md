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


<br><br>


### 데몬쓰레드

일반쓰레드의 보조역활을 하며 일반쓰레드가 종료될 때 같이 종료된다.


```
package thread;

public class Ex13_7 implements Runnable {
	
	static boolean autoSave = false;
	
	public static void main(String[] args) {
		Thread t = new Thread(new Ex13_7());
		t.setDaemon(true);
		t.start();
		
		for(int i=1; i <=10; i++) {
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e) {}
			System.out.println(i);
			
			if(i==5) autoSave = true;
		}
		
		System.out.println("프로그램을 종료합니다.");
		
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(3 * 1000);
			} catch (InterruptedException e) {
			}
			
			// autoSave의 값이 true이면 autoSave() 를 호출한다.
			if(autoSave) autoSave();
			
		}
	}
	
	public void autoSave() {
		System.out.println("작업파일이 자동저장되었습니다.");
	}

}


/*
결과

1
2
3
4
5
작업파일이 자동저장되었습니다.
6
7
8
작업파일이 자동저장되었습니다.
9
10
프로그램을 종료합니다.

*/


```

<br><br>


### sleep() - 정지


```
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

/*
결과

----------------||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||-------------------|||||||||||||||||||||------------------------------------------------------------------------------------------------------------------|||------------------------------||||||||||---------||||||||||||||-------------------------|||||||||||||||||||||||||||||||||--|||||||---------------------||||||-----------|||||||--||||||------------------------|||-----||||----------------------|||||||||||||||<<th1 종료>>
|||||||||||||||||||||||||||||||||||||||||||||||||||||<<th2 종료>>
<<main 종료>>

*/

```

- sleep 메서드는 현재 실행 중인 쓰레드를 중지시킨다. ( th1.sleep 의미 없음)
- sleep 메서드는 try ~catch 문 안에서 사용하는 것이 일반적이다.


<br>

### interrupt() - 실행 취소


```
package thread;

import javax.swing.JOptionPane;

public class Ex13_9 {

	public static void main(String[] args) {
		ThreadEx9_1 th1 = new ThreadEx9_1();
		th1.start(); // 쓰레드 시작
		
		String input = JOptionPane.showInputDialog("아무 값이나 입력하세요");
		System.out.println("입력하신 값은 " + input + "입니다.");
		th1.interrupt();
		System.out.println("isInterrupted() :" + th1.isInterrupted());
	}

}

class ThreadEx9_1 extends Thread {
	public void run() {
		int i = 10;
		
		while(i !=0 && !isInterrupted()) {
			System.out.println(i--);
			for(long x=0; x < 2500000000L; x++);
		}
		System.out.println("카운트가 종료되었습니다.");
	}
}

/*
결과

10
9
8
7
6
입력하신 값은 123입니다.
isInterrupted() :true
카운트가 종료되었습니다.

*/

```

- 쓰레드가 실행 중 일때 interrupt() 메서드가 '참' 일 때 쓰레드를 종료한다. 
- 실행 중이 아닐 떄 interrupt() 가 '거짓'으로 나온다.


<br><br>

### suspend(), resume(), stop()

과거에는 사용했지만, 현재는 권고사항이 아니다.

<br><br>


### join(), yield() - 대기


```
package thread;

public class Ex13_11 {
	static long startTime = 0;

	public static void main(String[] args) {
		ThreadEx11_1 th1 = new ThreadEx11_1();
		ThreadEx11_2 th2 = new ThreadEx11_2();
		th1.start();
		th2.start();
		startTime = System.currentTimeMillis();
		
		try {
			th1.join(); // main쓰레드가 th1 의 작업이 끝날 때까지 기다린다.
			th2.join(); // main쓰레드가 th2 의 작업이 끝날 때까지 기다린다.
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println("소요시간:" + (System.currentTimeMillis() - Ex13_11.startTime));
		
	}

}


class ThreadEx11_1 extends Thread {
	public void run() {
		for(int i=0; i < 300; i++) {
			System.out.print(new String("-"));
		}
	}
}

class ThreadEx11_2 extends Thread {
	public void run() {
		for(int i=0; i < 300; i++) {
			System.out.print(new String("|"));
		}
	}
}

/*
결과

---||||||----------------||||||||||||||||||||-----------------------------------------------------------------------------||------------------||||||||||||-------------------|||||||||||||||||||||||||||||||||||||||||||------------------------------||||-----------------------------------------------------------------------------------------------------------------------------------|||||||||-----||||||||||-||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||소요시간:11

*/

```

- 쓰레드를 실행 시킨 수 join() 메서드로, main 쓰레드가 ThreadEx11_1, ThreadEx11_2 의 실행이 끝날 때까지 기다린다.


<br><br>


### 동기화

같은 쓰레드 메서드를 실행 하였을 때 서로 간섭을 받지않기 위해, 


```
package thread;

public class Ex13_12 {

	public static void main(String[] args) {
		Runnable r = new RunnableEx12();
		new Thread(r).start();
		new Thread(r).start();
	}
}

class Account {
	private int balance = 1000;
	
	public int getBalance() {
		return balance;
	}
	
	public void withdraw(int money) {
		if(balance >= money) {
			try { Thread.sleep(1000);  } catch(InterruptedException e) {}
			balance -= money;
		}
	}
}

class RunnableEx12 implements Runnable {
	
	Account acc = new Account();

	@Override
	public void run() {
		while(acc.getBalance() > 0) {
			int money = (int)(Math.random() * 3 + 1) * 100;
			
			acc.withdraw(money);
			System.out.println("money: "+ money + ", balance:" + acc.getBalance());
		}
	}
	
}

/*
결과

money: 300, balance:700
money: 200, balance:700
money: 300, balance:400
money: 100, balance:400
money: 200, balance:100
money: 200, balance:100
money: 300, balance:100
money: 300, balance:100
money: 200, balance:100
money: 300, balance:100
money: 200, balance:100
money: 200, balance:100
money: 300, balance:100
money: 300, balance:100
money: 200, balance:100
money: 300, balance:100
money: 300, balance:100
money: 100, balance:-100 // 음수가 나옴. 
money: 100, balance:-100

*/

```

- 같은 Runnable 쓰레드의 run() 메서드를 동시에 2개를 실행했다.
- 서로 교착상태가 되어서 음수가 나오게 되었다.
- 동기화 메서드를 통해 서로 싸우지 않도록 해결할 수 있다.


<br><br>


### synchronized 

```
package thread;

public class Ex13_13 {

	public static void main(String[] args) {
		Runnable r = new RunnableEx13();
		new Thread(r).start();
		new Thread(r).start();		
	}
}

class Account2 {
	private int balance = 1000;
	
	public int getBalance() {
		return balance;
	}
	
	public synchronized void withdraw(int money) {
		if(balance >= money) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			balance -= money;
		}
	}
}

class RunnableEx13 implements Runnable {
	
	Account2 acc = new Account2();

	@Override
	public void run() {
		while(acc.getBalance() > 0) {
			int money = (int)(Math.random() *3 + 1) * 100;
			acc.withdraw(money);
			System.out.println("money :"  +  money  + ", balance : " +acc.getBalance());
		}
	}
	
}
```

- 앞의 예제에서 withdraw() 메서드 앞에 synchronized 지시자를 붙인거 외에는 별다른게 없다.
- synchronized 를 사용할 때 반드시 메서드안에서 참조하는 변수는 private 로 지정해야한다. 
- 그렇지 않으면 synchronized 를 붙여도 의미가없다. 외부에서 변경가능하기 때문이다.
 
 <br><br>
 
### wait


```
package thread;

import java.util.ArrayList;

class Customer2 implements Runnable {
	private Table2 table;
	private String food;	
	
	Customer2(Table2 table, String food) {
		this.table = table;
		this.food = food;
	}
	
	public void run() {
		while(true) {
			try {Thread.sleep(100);} catch (InterruptedException e) {}
			String name = Thread.currentThread().getName(); // Thread 의 두번째 인자로 넘긴 "문자열"
			
			table.remove(food);
			System.out.println(name + " ate a " + food);
		}
	}
}

class Cook2 implements Runnable {
	
	private Table2 table;
	
	Cook2(Table2 table) {this.table = table;}

	@Override
	public void run() {
		while(true) {
			int idx = (int)(Math.random() * table.dishNum());
			table.add(table.dishNames[idx]);
			try {Thread.sleep(10);} catch(InterruptedException e) {}
		}		
	}
}

class Table2 {
	String[] dishNames = {"donut", "donut", "burger"};
	final int MAX_FOOD = 6;
	private ArrayList<String> dishes = new ArrayList<String>();
	
	public synchronized void add(String dish) {
		while(dishes.size() >= MAX_FOOD) {
			String name = Thread.currentThread().getName();
			System.out.println(name + " is waiting.");
			try {
				wait();
				Thread.sleep(500);
			}catch(InterruptedException e) {}
		}
		dishes.add(dish);
		System.out.println("Dishes: " + dishes.toString());
	}
	
	
	public void remove(String dishName) {
		synchronized (this) {
			String name = Thread.currentThread().getName();
			
			while(dishes.size() == 0) {
				System.out.println(name + " is waiting");
				try {
					wait();
					Thread.sleep(500);
				} catch (InterruptedException e) {}
			}
			
			
			while(true) {
				for(int i=0; i < dishes.size(); i++) {
					if(dishName.equals(dishes.get(i))) {
						dishes.remove(i);
						notify();
						return;
					}
				}
				
				try {
					System.out.println(name + " is wating.");
					wait();
					Thread.sleep(500);
				}catch(InterruptedException e) {}
			}
		}
	}
	
	public int dishNum() { return dishNames.length; }
	
}


public class Ex13_15 {
	public static void main(String[] args) throws Exception {
		Table2 table = new Table2();
		
		new Thread(new Cook2(table), "COOK").start();
		new Thread(new Customer2(table, "donut"), "CUST1").start();
		new Thread(new Customer2(table, "burger"), "CUST2").start();
		Thread.sleep(2000);
		System.exit(0);
		
	}
}

/*
결과

Dishes: [burger]
Dishes: [burger, donut]
Dishes: [burger, donut, donut]
Dishes: [burger, donut, donut, burger]
Dishes: [burger, donut, donut, burger, burger]
Dishes: [burger, donut, donut, burger, burger, donut]
COOK is waiting.
CUST1 ate a donut
Dishes: [burger, donut, burger, burger, donut, burger]
CUST1 ate a donut
CUST2 ate a burger
Dishes: [burger, burger, donut, burger, donut]
Dishes: [burger, burger, donut, burger, donut, burger]
COOK is waiting.
CUST1 ate a donut
Dishes: [burger, burger, burger, donut, burger, burger]
CUST1 ate a donut
CUST2 ate a burger
Dishes: [burger, burger, burger, burger, donut]
Dishes: [burger, burger, burger, burger, donut, burger]
COOK is waiting.
CUST1 ate a donut
Dishes: [burger, burger, burger, burger, burger, burger]
CUST1 is wating.
CUST2 ate a burger

*/


```
 