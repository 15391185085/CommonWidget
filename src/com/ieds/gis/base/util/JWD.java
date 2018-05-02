package com.ieds.gis.base.util;

public class JWD {
	static double Rc = 6378137; // 赤道半径

	static double Rj = 6356725; // 极半径
	double m_LoDeg, m_LoMin, m_LoSec; // longtitude 经度
	double m_LaDeg, m_LaMin, m_LaSec;
	double m_Longitude, m_Latitude;
	double m_RadLo, m_RadLa;
	double Ec;
	double Ed;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// JWD B = JWD.GetJWDB(113.344,23.1346, 0.112, 130);
		// System.out.println(B.m_Longitude);
		// System.out.println(B.m_Latitude);
		JWD A = new JWD(0, 0);
		JWD B = new JWD(-1, 0);
		System.out.println(JWD.angle(A, B));
	}

	public JWD(double longitude, double latitude) {
		m_LoDeg = (int) longitude;
		m_LoMin = (int) ((longitude - m_LoDeg) * 60);
		m_LoSec = (longitude - m_LoDeg - m_LoMin / 60.) * 3600;

		m_LaDeg = (int) latitude;
		m_LaMin = (int) ((latitude - m_LaDeg) * 60);
		m_LaSec = (latitude - m_LaDeg - m_LaMin / 60.) * 3600;

		m_Longitude = longitude;
		m_Latitude = latitude;
		m_RadLo = longitude * Math.PI / 180.;
		m_RadLa = latitude * Math.PI / 180.;
		Ec = Rj + (Rc - Rj) * (90. - m_Latitude) / 90.;
		Ed = Ec * Math.cos(m_RadLa);
	}

	public double getM_Longitude() {
		return m_Longitude;
	}

	public double getM_Latitude() {
		return m_Latitude;
	}


	// ! 计算点A 和 点B的经纬度，求他们的距离和点B相对于点A的方位
	/*
	 * ! \param A A点经纬度 \param B B点经纬度 \param angle B相对于A的方位, 不需要返回该值，则将其设为空
	 * \return A点B点的角度
	 */
	public static double angle(JWD A, JWD B) {
		double dx = (B.m_RadLo - A.m_RadLo) * A.Ed;
		double dy = (B.m_RadLa - A.m_RadLa) * A.Ec;
		// double out = Math.sqrt(dx * dx + dy * dy);
		double angle = 0.0;
		angle = Math.atan(Math.abs(dx / dy)) * 180. / Math.PI;
		// 判断象限
		double dLo = B.m_Longitude - A.m_Longitude;
		double dLa = B.m_Latitude - A.m_Latitude;

		if (dLo > 0 && dLa <= 0) {
			angle = (90. - angle) + 90.;
		} else if (dLo <= 0 && dLa < 0) {
			angle = angle + 180.;
		} else if (dLo < 0 && dLa >= 0) {
			angle = (90. - angle) + 270;
		}
		return angle;
	}

}