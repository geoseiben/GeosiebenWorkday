package com.geosieben.gsbworkday.entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data 
@Entity
@Table(name="hosts")
public class Hosts {
    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int hostid;
    private String hostType;
    private String hostname;
    private String lanNo;
    private String processor;
    private String ssd;
    private String hdd;
    private String ram;
    private String graphics;
    private String cabinet;
    private String os;
    private String ipAddress;
    private String vram;
    private String additionalHdd;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="monitor1")
    private ItAsset monitor1;
    private String mon1vc;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="monitor2")
    private ItAsset monitor2;
    private String mon2vc;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="mouse")
    private ItAsset mouse;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="keyboard")
    private ItAsset keyboard;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="emmitter")
    private ItAsset emmitter;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="goggle")
    private ItAsset goggle;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="headphone")
    private ItAsset headphone;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="laptop")
    private ItAsset laptop;

    private String ad1;

    private String ad2;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="ups")
    private ItAsset ups;
    private int isAvailable=1;
    private int status=0;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="createdBy")
    private EmployeeBasicInfo createdBy;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="leaseid")
    private AssetLease assetLease;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name="assignmentId")
    private AssetAssignment assetAssignment;
    

	public Hosts() {
	}


	public Hosts(int hostid, String hostType, String hostname, String lanNo, String processor, String ssd, String hdd,
			String ram, String graphics, String cabinet, String os, String ipAddress, String vram, String additionalHdd,
			ItAsset monitor1, String mon1vc, ItAsset monitor2, String mon2vc, ItAsset mouse, ItAsset keyboard,
			ItAsset emmitter, ItAsset goggle, ItAsset headphone, ItAsset laptop, String ad1, String ad2, ItAsset ups,
			int isAvailable, int status, EmployeeBasicInfo createdBy, AssetLease assetLease,
			AssetAssignment assetAssignment) {
		this.hostid = hostid;
		this.hostType = hostType;
		this.hostname = hostname;
		this.lanNo = lanNo;
		this.processor = processor;
		this.ssd = ssd;
		this.hdd = hdd;
		this.ram = ram;
		this.graphics = graphics;
		this.cabinet = cabinet;
		this.os = os;
		this.ipAddress = ipAddress;
		this.vram = vram;
		this.additionalHdd = additionalHdd;
		this.monitor1 = monitor1;
		this.mon1vc = mon1vc;
		this.monitor2 = monitor2;
		this.mon2vc = mon2vc;
		this.mouse = mouse;
		this.keyboard = keyboard;
		this.emmitter = emmitter;
		this.goggle = goggle;
		this.headphone = headphone;
		this.laptop = laptop;
		this.ad1 = ad1;
		this.ad2 = ad2;
		this.ups = ups;
		this.isAvailable = isAvailable;
		this.status = status;
		this.createdBy = createdBy;
		this.assetLease = assetLease;
		this.assetAssignment = assetAssignment;
	}

    
}