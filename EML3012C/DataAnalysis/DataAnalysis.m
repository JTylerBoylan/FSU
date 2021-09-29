% Written by Jonathan T. Boylan
% EML 3012C
% Dr. Dorr Campbell

classdef DataAnalysis
    properties
        data % Measurement Data
        ile % Instrumental Limit of Error
        le_func % Function to determine Overall Limit of Error
        sle_func % Function to determine Statistical Limit of Error
    end
    
    methods
        
        % Constructor
        function obj = DataAnalysis(data)
            obj.data = data;
            obj.ile = 0;
            obj.le_func = @(ile,sle) norm([ile sle]); % RSS Method
            obj.sle_func = @(n,std) 3*std/sqrt(n);
        end
        
        % Member functions
        function l = size(obj)
            l = length(obj.data);
        end
        
        function b = best(obj)
            b = mean(obj.data);
        end
        
        function sd = stdev(obj)
           if (obj.size > 30)
               sd = std(obj.data,1); % normalizes by N
           else
               sd = std(obj.data,0); % normalizes by N-1
           end
        end
        
        function s = sle(obj)
            s = obj.sle_func(obj.size,obj.stdev);
        end
        
        function l = le(obj)
            l = obj.le_func(obj.ile,obj.sle);
        end
        
        function str = tostr(obj)
            %TOSTR Converts DataAnalysis to string in best +- error format
            rl = round(obj.le,1,'significant');
            huh = ceil(log10(abs(obj.best))) - floor(log10(obj.le));
            % ^ Don't ask me why this works so well
            rb = round(obj.best,huh,'significant');
            b = num2str(rb);
            e = num2str(rl);
            c = char(177);
            str = [b,c,e];
        end
        
        function ub = upper(obj)
            ub = obj.best + obj.le;
        end
        
        function lb = lower(obj)
            lb = obj.best - obj.le;
        end
        
        function obj = reject(obj, criteria)
            nor = abs(obj.data - obj.best)./obj.stdev;
            obj.data = obj.data(nor < criteria);
        end
        
        % Operator Overloading
        function obj = uminus(obj)
            obj.data = obj.data .* -1;
        end
        
        function obj = plus(obj, obj2)
            mu = obj.best + obj2.best;
            nle = norm([obj.le obj2.le]);
            sigma = obj.sle2std(obj.sle_func,nle,2);
            alpha = sigma/sqrt(2);
            obj.data = [mu-alpha mu+alpha];
            obj.ile = 0;
        end
        
        function obj = minus(obj, obj2)
            obj = plus(obj,-obj2);
        end
        
        function obj = mtimes(obj, obj2)
            mu = obj.best*obj2.best;
            nle = mu*norm([obj.le/obj.best obj2.le/obj2.best]);
            sigma = obj.sle2std(obj.sle_func,nle,2);
            alpha = sigma/sqrt(2);
            obj.data = [mu-alpha mu+alpha];
            obj.ile = 0;
        end
        
        function obj = mrdivide(obj, obj2)
            mu = obj.best/obj2.best;
            nle = mu*norm([obj.le/obj.best obj2.le/obj2.best]);
            sigma = obj.sle2std(obj.sle_func,nle,2);
            alpha = sigma/sqrt(2);
            obj.data = [mu-alpha mu+alpha];
            obj.ile = 0;
        end
        
        function obj = mpower(obj, n)
            mu = obj.best^n;
            nle = n*obj.le/obj.best;
            sigma = obj.sle2std(obj.sle_func,nle,2);
            alpha = sigma/sqrt(2);
            obj.data = [mu-alpha mu+alpha];
            obj.ile = 0;
        end
        
    end % End class methods
    
    methods(Static)
        
        function std = sle2std(sle_func, sle, n)
            %SLE2STD gives the standard deviation of the data given the
            %S.L.E function, S.L.E, and number of elements. Used for error 
            %propogation functions
            syms x;
            f(x) = sle_func(n,x);
            g = finverse(f);
            std = double(g(sle));
        end
        
    end % End static methods
    
end % End classdef

