//
//  ViewController.swift
//  XSwift
//
//  Created by kuaXueWang on 2017/5/24.
//  Copyright © 2017年 xie. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBOutlet weak var helloButton: UIButton!

    @IBAction func showAlert(_ sender: Any) {
        self.helloButton.setTitle("clicked", for: UIControlState.normal)
    }
}

