import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Main {

    public static void play() {
        try {
            File song = new File("ringtones/ghills_mix.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(song);
            Clip clip = AudioSystem.getClip();

            // Abrir o stream de áudio e carregar no Clip
            clip.open(audioInputStream);
            clip.start();
            // Aguardar a reprodução terminar antes de encerrar a função
            while (!clip.isRunning()) {
                Thread.sleep(10);
            }
            while (clip.isRunning()) {
                Thread.sleep(10);
            }
            clip.close();
            audioInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int minutes = scanner.nextInt();
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final Runnable contador = new Runnable() {
            int timeLeft = minutes * 60;
            @Override
            public void run() {
                if (timeLeft >= 0) {
                    if(timeLeft == 1200) System.out.println("20 minutos restantes");
                    if(timeLeft == 900) System.out.println("15 minutos restantes");
                    if(timeLeft == 600) System.out.println("10 minutos restantes");
                    if(timeLeft == 300) System.out.println("5 minutos restantes");
                    timeLeft--;
                } else {
                    System.out.println("DESCANSE BOY!");
                    play();
                    scheduler.shutdown();
                }
            }
        };
        scheduler.scheduleAtFixedRate(contador, 0, 1, TimeUnit.SECONDS);
    }
}
