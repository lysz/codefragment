package com.aora.wifi.tools;



public class LatitudeLontitudeUtil {
    
    public static class Point{
        
        public Point(double x, double y) {
            super();
            this.x = x;
            this.y = y;
        }
        public double x;
        public double y;
    }
    
    /**
    * Calculates the end-point from a given source at a given range (meters)
    * and bearing (degrees). This methods uses simple geometry equations to
    * calculate the end-point.
    * 
    * @param point
    *            Point of origin
    * @param range
    *            Range in meters
    * @param bearing
    *            Bearing in degrees
    * @return End-point from the source given the desired range and bearing.
    */
    private static Point calculateDerivedPosition(Point point,
                double range, double bearing)
        {
            double EarthRadius = 6371000; // m

            double latA = Math.toRadians(point.x);
            double lonA = Math.toRadians(point.y);
            double angularDistance = range / EarthRadius;
            double trueCourse = Math.toRadians(bearing);

            double lat = Math.asin(
                    Math.sin(latA) * Math.cos(angularDistance) +
                            Math.cos(latA) * Math.sin(angularDistance)
                            * Math.cos(trueCourse));

            double dlon = Math.atan2(
                    Math.sin(trueCourse) * Math.sin(angularDistance)
                            * Math.cos(latA),
                    Math.cos(angularDistance) - Math.sin(latA) * Math.sin(lat));

            double lon = ((lonA + dlon + Math.PI) % (Math.PI * 2)) - Math.PI;

            lat = Math.toDegrees(lat);
            lon = Math.toDegrees(lon);

            Point newPoint = new Point((double) lat, (double) lon);

            return newPoint;
     }
    
    public static Point[] getRectangle4Point_v2(double lat, double lng, double radius){
        
        final double mult = 1.1; // mult = 1.1; is more reliable
        Point[] ps = new Point[4];
        
        Point center = new Point(lat, lng);
        
        Point p1 = calculateDerivedPosition(center, mult * radius, 0);
        Point p2 = calculateDerivedPosition(center, mult * radius, 90);
        Point p3 = calculateDerivedPosition(center, mult * radius, 180);
        Point p4 = calculateDerivedPosition(center, mult * radius, 270);
        
        ps[0] = p1;
        ps[1] = p2;
        ps[2] = p3;
        ps[3] = p4;
        
        return ps;
    }
    
    
    

    // http://blog.charlee.li/location-search/

    /** 地球半径 */
    private static final double EARTH_RADIUS = 6371000;
    
    /** 范围距离 */
    private double              distance;
    /** 左上角 */
    private Location            left_top     = null;
    /** 右上角 */
    private Location            right_top    = null;
    /** 左下角 */
    private Location            left_bottom  = null;
    /** 右下角 */
    private Location            right_bottom = null;

    private LatitudeLontitudeUtil(double distance) {
        this.distance = distance;
    }

    private void getRectangle4Point(double lat, double lng) {

        double dlng = 2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS))
                / Math.cos(lat));
        dlng = Math.toDegrees(dlng);

        double dlat = distance / EARTH_RADIUS;
        dlat = Math.toDegrees(dlat); // # 弧度转换成角度

        left_top = new Location(lat + dlat, lng - dlng);
        right_top = new Location(lat + dlat, lng + dlng);
        left_bottom = new Location(lat - dlat, lng - dlng);
        right_bottom = new Location(lat - dlat, lng + dlng);

    }

    public static double hav(double theta) {
        double s = Math.sin(theta / 2);
        return s * s;
    }

    public static double getDistance(double lat0, double lng0, double lat1,
            double lng1) {

        lat0 = Math.toRadians(lat0);
        lat1 = Math.toRadians(lat1);
        lng0 = Math.toRadians(lng0);
        lng1 = Math.toRadians(lng1);

        double dlng = Math.abs(lng0 - lng1);
        double dlat = Math.abs(lat0 - lat1);
        double h = hav(dlat) + Math.cos(lat0) * Math.cos(lat1) * hav(dlng);
        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));

        return distance;
    }

    public static Location[] getRectangle4Point(double lat, double lng,
            double distance) {

        LatitudeLontitudeUtil llu = new LatitudeLontitudeUtil(distance);
        llu.getRectangle4Point(lat, lng);

        Location[] locations = new Location[4];
        locations[0] = llu.left_top;
        locations[1] = llu.right_top;
        locations[2] = llu.left_bottom;
        locations[3] = llu.right_bottom;

        return locations;
    }

    public static void main(String[] args) {
        double lat = 22.53602;
        double lng = 114.031462;
        double distance = 999999d;

        Location[] locations = LatitudeLontitudeUtil.getRectangle4Point(lat,
                lng, distance);

        String sql = "SELECT * FROM place WHERE lat > "
                + locations[2].getLatitude() + " AND lat < "
                + locations[0].getLatitude() + " AND lng > "
                + locations[0].getLongitude() + " AND lng < "
                + locations[1].getLongitude();
        
        System.out.println(sql);

//        double lat1 = 30.495503391970406;
//        double lng1 = 120.49261708577215;
//        double d = LatitudeLontitudeUtil.getDistance(lat, lng, lat1, lng1);
//        System.out.println(d);
    }
    
    
    public static class Location {
        
        private double latitude;
        private double longitude;

        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

    }
}
