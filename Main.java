public class Main {

    /**
     * Run this for local conversion of C:\\picture.jpg
     */
    public static void main(String[] args) {
       IMGRead ir = new IMGRead();
       Map<String, Object[]> data = ir.read("C:\\picture.jpg");
        POIWrite pw = new POIWrite();
       pw.write(data);
    }

}
