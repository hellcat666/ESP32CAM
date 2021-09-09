class ESP32Camera {

    #cameraName;
    #ipAddress;

    #statusUrl;
    #controlUrl;

    #jsonData;

    #frameSize;
    #quality;
    #brightness;
    #contrast;
    #saturation;
    #specialEffect;
    #wbMode;
    #awb;
    #awbGain;
    #aec;
    #aec2;
    #aeLevel;
    #aecValue;
    #agc;
    #agcGain;
    #gainCeiling;
    #bpc;
    #wpc;
    #rawGMA;
    #lensCorrection;
    #vFlip;
    #hMirror;
    #dcw;
    #colorBar;
    #faceDetection;
    #faceEnroll;
    #faceRecognition;


    constructor(name, ip) {
        this.#cameraName = name;
        this.#ipAddress = ip;
        this.setStatusUrl(ip);
        this.setControlUrl(ip);
    }


    setName(name) { this.#cameraName = name; }
    getName() { return this.#cameraName; }

    setIpAddress(ip) { this.#ipAddress = ip; }
    getIpAddress() { return this.#ipAddress; }

    setStatusUrl(ip) { this.#statusUrl = 'http://' + this.#ipAddress + '/status'; }
    getStatusUrl() { return this.#statusUrl; }
    setControlUrl(ip) { this.#controlUrl = 'http://' + this.#ipAddress + '/control?'; }
    getControlUrl() { return this.#controlUrl; }



    setJSONData(data) { this.#jsonData = data; }
    getJSONData() { return this.#jsonData; }

    setFrameSize(size) { this.#frameSize = size; }
    getFrameSize() { return this.#frameSize; }

    async setResolution(resolution) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setFrameSize(resolution);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=framesize&val=" + this.getFrameSize(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setQuality(quality) { this.#quality = quality; }
    getQuality() { return this.#quality; }
    async setCameraQuality(quality) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setQuality(quality);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=quality&val=" + this.getQuality(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setBrightness(brightness) { this.#brightness = brightness; }
    getBrightness() { return this.#brightness; }
    async setCameraBrightness(brightness) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setBrightness(brightness);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=brightness&val=" + this.getBrightness(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setContrast(contrast) { this.#contrast = contrast; }
    getContrast() { return this.#contrast; }
    async setCameraContrast(contrast) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setContrast(contrast);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=contrast&val=" + this.getContrast(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setSaturation(saturation) { this.#saturation = saturation; }
    getSaturation() { return this.saturation; }
    async setCameraSaturation(saturation) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setSaturation(saturation);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=saturation&val=" + this.getSaturation(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setSpecialEffect(effect) { this.#specialEffect = effect; }
    getSpecialEffect() { return this.#specialEffect; }
    async setCameraEffect(effect) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setSpecialEffect(effect);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=special_effect&val=" + this.getSpecialEffect(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setAWB(awb) { this.#awb = awb; }
    getAWB() { return this.#awb; }
    async setCameraAWB(awb) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setAWB(awb);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=awb&val=" + this.getAWB(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setAWBGain(awbGain) { this.#awbGain = awbGain; }
    getAWBGain() { return this.#awbGain; }
    async setCameraAWBGain(awbGain) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setAWBGain(awbGain);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=awb_gain&val=" + this.getAWBGain(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setWBMode(wbMode) { this.#wbMode = wbMode; }
    getWBMode() { return this.#wbMode; }
    async setCameraWBMode(wbMode) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setWBMode(wbMode);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=wb_mode&val=" + this.getWBMode(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setAEC(aec) { this.#aec = aec; };
    getAEC() { return this.#aec; }
    async setCameraAEC(aec) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setAEC(aec);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=aec&val=" + this.getAEC(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setAEC2(aec2) { this.#aec2 = aec2; }
    getAEC2() { return this.#aec2; }
    async setCameraAEC2(aec2) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setAEC2(aec2);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=aec2&val=" + this.getAEC2(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setAELevel(level) { this.aeLevel = level; }
    getAELevel() { return this.#aeLevel; }
    async setCameraAELevel(aeLevel) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setAELevel(aeLevel);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=ae_level&val=" + this.getAELevel(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setAECValue(value) { this.#aecValue = value; }
    getAECValue() { return this.#aecValue; }

    setAGC(agc) { this.#agc = agc; }
    getAGC() { return this.#agc; }
    async setCameraAGC(agc) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setAGC(agc);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=agc&val=" + this.getAGC(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }


    setAGCGain(gain) { this.#agcGain = gain; }
    getAGCGain() { return this.#agcGain; }

    setGainCeiling(ceiling) { this.#gainCeiling = ceiling; }
    getGainCeiling() { return this.#gainCeiling; }

    setBPC(bpc) { this.#bpc = bpc; }
    getBPC() { return this.#bpc; }

    setWPC(wpc) { this.#wpc = wpc; }
    getWPC() { return this.#wpc; }

    setRawGMA(gma) { this.#rawGMA = gma; }
    getRawGMA()  { return this.#rawGMA; }

    setLensCorrection(correction) { this.#lensCorrection = correction; }
    getLensCorrection() { return this.#lensCorrection; }

    setHMirror(hmirror) { this.#hMirror = hmirror; }
    getHMirror() { return this.#hMirror; }
    async setCameraHMirror(hMirror) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setHMirror(hMirror);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=hmirror&val=" + this.getHMirror(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setVFlip(vflip) { this.#vFlip = vflip; }
    getVFlip() { return this.#vFlip; }
    async setCameraVFlip(vFlip) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setVFlip(vFlip);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=vflip&val=" + this.getVFlip(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setDCW(dcw) { this.#dcw = dcw; }
    getDCW() { return this.#dcw; }

    setColorBar(colorbar) { this.#colorBar = colorbar; }
    getColorBar() { return this.#colorBar; }
    async setCameraColorBar(colorBar) {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
        this.setColorBar(colorBar);
        const response = await $.ajax({
            url: this.getControlUrl() + "cmd=colorbar&val=" + this.getColorBar(),
            type: 'GET',
            crossDomain: true,
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }

    setFaceDetection(detection) { this.#faceDetection = detection; }
    getFaceDetection() { return this.#faceDetection; }

    setFaceEnroll(enroll) { this.#faceEnroll = enroll; }
    getFaceEnroll() { return this.#faceEnroll; }

    setFaceRecognition(recognition) { this.#faceRecognition = recognition; }
    getFaceRecognition() { return this.#faceRecognition; }

    async getCameraSettings() {
    var parent = this;      // Pointer (sort of) representing the class
                            // It is used to reference the class where this
                            // represents the local scope (e.g inside the ajax call)
    console.log("ESP32Camera.getCameraSettings()");
    const response = await $.ajax({
            url: this.getStatusUrl(),
            type: 'GET',
            crossDomain: true,
            dataType: 'jsonp',
            data: { format: 'json' },
            success: function(data) {
                console.log('SUCCESS');
                parent.setJSONData(data);
                parent.setFrameSize(data['framesize']);
                parent.setQuality(data['quality']);
                parent.setBrightness(data['brightness']);
                parent.setContrast(data['contrast']);
                parent.setSaturation(data['saturation']);
                parent.setSpecialEffect(data['special_effect']);
                parent.setWBMode(data['wb_mode']);
                parent.setAWB(data['awb']);
                parent.setAWBGain(data['awb_gain']);
                parent.setAEC(data['aec']);
                parent.setAEC2(data['aec2']);
                parent.setAELevel(data['ae_level']);
                parent.setAECValue(data['aec_value']);
                parent.setAGC(data['agc']);
                parent.setAGCGain(data['agc_gain']);
                parent.setGainCeiling(data['gainceiling']);
                parent.setBPC(data['bpc']);
                parent.setWPC(data['wpc']);
                parent.setRawGMA(data['raw_gma']);
                parent.setLensCorrection(data['lenc']);
                parent.setVFlip(data['vflip']);
                parent.setHMirror(data['hmirror']);
                parent.setDCW(data['dcw']);
                parent.setColorBar(data['colorbar']);
                parent.setFaceDetection(data['face_detect']);
                parent.setFaceEnroll(data['face_enroll']);
                parent.setFaceRecognition(data['face_recognize']);
//                parent.show();
            },
            error: function(error) {
                console.log(error);
            }
        });
        return response;
    }



    show() {
        console.log('Name            :' + this.getName() + '\n');
        console.log('IP Address       :' + this.getIpAddress() + '\n');
        console.log('Frame Size       :' + this.getFrameSize() + '\n');
        console.log('Quality          :' + this.getQuality() + '\n');
        console.log('Brightness       :' + this.getBrightness() + '\n');
        console.log('Contrast         :' + this.getContrast() + '\n');
        console.log('Saturation       :' + this.getSaturation() + '\n');
        console.log('Special Effect   :' + this.getSpecialEffect() + '\n');
        console.log('WB Mode          :' + this.getWBMode() + '\n');
        console.log('AWB              :' + this.getAWB() + '\n');
        console.log('AWB Gain         :' + this.getAWBGain() + '\n');
        console.log('AEC              :' + this.getAEC() + '\n');
        console.log('AEC2             :' + this.getAEC2() + '\n');
        console.log('AE Level         :' + this.getAELevel() + '\n');
        console.log('AEC Value        :' + this.getAECValue() + '\n');
        console.log('AGC              :' + this.getAGC() + '\n');
        console.log('AGC Gain         :' + this.getAGCGain() + '\n');
        console.log('Gain Ceiling     :' + this.getGainCeiling() + '\n');
        console.log('BPC              :' + this.getBPC() + '\n');
        console.log('WPC              :' + this.getWPC() + '\n');
        console.log('Raw GMA          :' + this.getRawGMA() + '\n');
        console.log('Lens Correction  :' + this.getLensCorrection() + '\n');
        console.log('V-Flip           :' + this.getVFlip() + '\n');
        console.log('H-Mirror         :' + this.getHMirror() + '\n');
        console.log('DCW              :' + this.getDCW() + '\n');
        console.log('ColorBar         :' + this.getColorBar() + '\n');
        console.log('Face Detection   :' + this.getFaceDetection() + '\n');
        console.log('Face Enroll      :' + this.getFaceEnroll() + '\n');
        console.log('Face Recognition :' + this.getIpAddress() + '\n');
    }
}