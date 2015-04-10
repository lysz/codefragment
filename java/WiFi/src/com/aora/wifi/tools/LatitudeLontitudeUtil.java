package com.aora.wifi.tools;



public class LatitudeLontitudeUtil {

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
        double lat = 30.500;
        double lng = 120.500;
        double distance = 2000d;

        Location[] locations = LatitudeLontitudeUtil.getRectangle4Point(lat,
                lng, distance);

        String sql = "SELECT * FROM place WHERE lat > "
                + locations[2].getLatitude() + " AND lat < "
                + locations[0].getLatitude() + " AND lng > "
                + locations[0].getLongitude() + " AND lng < "
                + locations[1].getLongitude();
        
        System.out.println(sql);

        double lat1 = 30.495503391970406;
        double lng1 = 120.49261708577215;
        double d = LatitudeLontitudeUtil.getDistance(lat, lng, lat1, lng1);
        System.out.println(d);
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
